package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.view.StageRangeOfTeams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private final StatusService statusService;
    private final HintTakenService hintTakenService;
    private final TeamService teamService;
    private final CypherService cypherService;
    private final GameLogicService gameLogicService;

    @Autowired
    public ProgressService(final StatusService statusService,
                           final HintTakenService hintTakenService,
                           final TeamService teamService,
                           final CypherService cypherService,
                           final GameLogicService gameLogicService) {
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
        this.teamService = teamService;
        this.cypherService = cypherService;
        this.gameLogicService = gameLogicService;
    }

    public Map<Long, CypherStatus> getTeamsStatuses(final Cypher cypher) {
        List<Status> statuses = statusService.getAllByCypher(cypher);
        Map<Long, CypherStatus> teamsStatuses = new HashMap<>();
        statuses.forEach(status -> {
            teamsStatuses.put(status.getTeam().getTeamId(), status.getCypherStatus());
        });
        return teamsStatuses;
    }

    public Map<Long, CypherStatus> getCyphersStatuses(final Team team) {
        List<Status> statuses = statusService.getAllByTeam(team);
        Map<Long, CypherStatus> cypherStatuses = new HashMap<>();
        statuses.forEach(status -> {
            cypherStatuses.put(status.getCypher().getCypherId(), status.getCypherStatus());
        });
        return cypherStatuses;
    }

    public Map<Long, String> setTakenHintsToMap(final Cypher cypher, final Team team) {
        List<Hint> hints = cypher.getHints();
        List<HintTaken> takenHints = hintTakenService.getTakenHintsOfTeam(team);
        Map<Long, String> takenHintsMap = new HashMap<>();
        hints.forEach(hint -> {
            takenHints.forEach(hintTaken -> {
                if (hintTaken.getHint() == hint) {
                    takenHintsMap.put(hint.getHintId(), hint.getText());
                }
            });
        });
        return takenHintsMap;
    }

    public boolean isGameStartedForAllTeams() {
        List<Team> teams = teamService.getAll();
        Cypher firstCypher = cypherService.getFirstOrderByStageAsc();
        for (Team team : teams) {
            if (!statusService.isStatusInDbByCypherAndTeam(firstCypher, team)) {
                return false;
            }
        }
        return true;
    }

    public List<Team> getSearchedTeams(final String searchString) {
        if (searchString == null) {
            return teamService.getAll();
        } else {
            return teamService.searchTeams(searchString);
        }
    }

    public StageRangeOfTeams getCurrentStageRangeOfAllTeams() {
        List<Team> teams = teamService.getAll();
        List<Team> teamsWithCypherPending = getTeamsWithPendingCypher(teams);
        List<Integer> stages = teamsWithCypherPending.stream()
                .map(this::getStageOfPendingCypherForTeam)
                .collect(Collectors.toList());
        StageRangeOfTeams stageRangeOfTeams = new StageRangeOfTeams(Collections.min(stages),
                Collections.max(stages));
        return stageRangeOfTeams;
    }

    public int getNumberOfFinishedTeams() {
        List<Team> teams = teamService.getAll();
        int numberOfFinishedTeams = (int) teams.stream()
                .filter(team -> gameLogicService.passedAllCyphers(team))
                .count();
        return numberOfFinishedTeams;
    }

    public List<Team> getTeamsWithPendingCypher(final List<Team> teams) {
        List<Team> teamsWithCypherPending = teams.stream()
                .filter(team -> hasCypherPending(team))
                .collect(Collectors.toList());
        return teamsWithCypherPending;
    }

    private boolean hasCypherPending(final Team team) {
        return statusService.getAllByTeam(team).stream()
                .anyMatch(status -> status.getCypherStatus() == CypherStatus.PENDING);
    }

    private boolean anyTeamHasCypherPending(final List<Team> teams) {
        return teams.stream()
                .anyMatch(team -> hasCypherPending(team));
    }

    private int getStageOfPendingCypherForTeam(final Team team) {
        List<Status> statuses = statusService.getAllByTeam(team);
        Status statusPending = statuses.stream()
                .filter(status -> status.getCypherStatus() == CypherStatus.PENDING)
                .findFirst()
                .get();
        return statusPending.getCypher().getStage();
    }
}
