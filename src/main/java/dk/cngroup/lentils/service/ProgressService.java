package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    private final StatusService statusService;

    @Autowired
    public ProgressService(StatusService statusService) {
        this.statusService = statusService;
    }

    public CypherStatus setSkipProgressStatus(Cypher cypher, Team team) {
        statusService.initializeStatusForTeamAndCypher(cypher, team);
        statusService.markCypherSkippedForTeam(cypher, team);
        return statusService.getCypherStatusForTeam(cypher, team);
    }

    public CypherStatus setPassProgressStatus(Cypher cypher, Team team) {
        statusService.initializeStatusForTeamAndCypher(cypher, team);
        statusService.markCypherSolvedForTeam(cypher, team);
        return statusService.getCypherStatusForTeam(cypher, team);
    }

    public CypherStatus setPendingProgressStatus(Cypher cypher, Team team) {
        statusService.initializeStatusForTeamAndCypher(cypher, team);
        statusService.markCypherPendingForTeam(cypher, team);
        return statusService.getCypherStatusForTeam(cypher, team);
    }
}
