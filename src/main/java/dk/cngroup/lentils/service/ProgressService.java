package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgressService {

    private final StatusService statusService;
    private final TeamService teamService;
    private final HintTakenService hintTakenService;

    @Autowired
    public ProgressService(final StatusService statusService,
                           final TeamService teamService,
                           final HintTakenService hintTakenService) {
        this.statusService = statusService;
        this.teamService = teamService;
        this.hintTakenService = hintTakenService;
    }

    public void makeCypher(final Cypher cypher, final Team team, final CypherStatus cypherStatus) {
        statusService.initializeStatusForTeamAndCypher(cypher, team);
        statusService.markCypher(cypher, team, cypherStatus);
    }

    public Map<Long, CypherStatus> getTeamsStatuses(final Cypher cypher) {
        List<Status> statuses = statusService.getAllByCypher(cypher);
        List<Team> teams = teamService.getAll();
        Map<Long, CypherStatus> teamsStatuses = new HashMap<>();
        teams.forEach(team -> setStatusOfTeamsToMap(statuses, teamsStatuses, team));
        return teamsStatuses;
    }

    private void setStatusOfTeamsToMap(final List<Status> statuses,
                                       final Map<Long, CypherStatus> teamsStatuses,
                                       final Team team) {
        statuses.forEach(status -> {
            if ((status.getTeam() == team)) {
                teamsStatuses.put(team.getTeamId(), status.getCypherStatus());
            }
        });
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
}
