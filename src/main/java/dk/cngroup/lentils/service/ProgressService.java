package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String getCurrentStageRangeOfAllTeams() {
        List<Team> teams =teamService.getAll();
        int teamsWithPendingCypher = countTeamsWithPendingCypher(teams);
        int[] stages = fillInStagesOfTeams(teams, teamsWithPendingCypher);
        int min = Arrays.stream(stages).min().getAsInt();
        int max = Arrays.stream(stages).max().getAsInt();
        return min + "-" + max;
    }

    public int getNumberOfFinishedTeams() {
        List<Team> teams = teamService.getAll();
        int numberOfFinishedTeams = 0;
        for (Team team : teams) {
            if (gameLogicService.passedAllCyphers(team)) {
                numberOfFinishedTeams++;
            }
        }
        return numberOfFinishedTeams;
    }

    private int[] fillInStagesOfTeams(List<Team> teams, int numberOfTeams) {
        int[] stages = new int[numberOfTeams];
        int counter = 0;
        for (Team team : teams) {
            if (hasCypherPending(team)) {
                stages[counter] = getStageOfPendingCypherForTeam(team);
                counter++;
            }
        }
        return stages;
    }

    private int countTeamsWithPendingCypher(List<Team> teams) {
        int i = 0;
        for (Team team : teams) {
            if (hasCypherPending(team)) {
                i++;
            }
        }
        return i;
    }

    private boolean hasCypherPending(Team team) {
        return statusService.getAllByTeam(team)
                .stream()
                .filter(status -> status.getCypherStatus() == CypherStatus.PENDING)
                .findAny()
                .isPresent();
    }

    private int getStageOfPendingCypherForTeam(final Team team) {
        List<Status> statuses = statusService.getAllByTeam(team);
        Status statusPending = statuses
                .stream()
                .filter(status -> status.getCypherStatus().equals(CypherStatus.PENDING))
                .findFirst()
                .get();
        return statusPending.getCypher().getStage();
    }
}
