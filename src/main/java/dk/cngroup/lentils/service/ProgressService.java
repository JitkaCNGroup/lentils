package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgressService {

    private final StatusService statusService;
    private final HintTakenService hintTakenService;

    @Autowired
    public ProgressService(final StatusService statusService,
                           final HintTakenService hintTakenService) {
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
    }

    public Map<Long, CypherStatus> getTeamsStatuses(final Cypher cypher) {
        List<Status> statuses = statusService.getAllByCypher(cypher);
        Map<Long, CypherStatus> teamsStatuses = new HashMap<>();
        statuses.forEach(status -> {
            teamsStatuses.put(status.getTeam().getTeamId(), status.getCypherStatus());
        });
        return teamsStatuses;
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
