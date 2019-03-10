package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
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
    private final TeamService teamService;

    @Autowired
    public ProgressService(StatusService statusService, TeamService teamService) {
        this.statusService = statusService;
        this.teamService = teamService;
    }

    public void setSkipProgressStatus(Cypher cypher, Team team) {
        statusService.initializeStatusForTeamAndCypher(cypher, team);
        statusService.markCypherSkippedForTeam(cypher, team);
    }

    public void setPassProgressStatus(Cypher cypher, Team team) {
        statusService.initializeStatusForTeamAndCypher(cypher, team);
        statusService.markCypherSolvedForTeam(cypher, team);
    }

    public void setPendingProgressStatus(Cypher cypher, Team team) {
        statusService.initializeStatusForTeamAndCypher(cypher, team);
        statusService.markCypherPendingForTeam(cypher, team);
    }

    public Map<Long, CypherStatus> getProgressStatusMap(Cypher cypher) {
        List<Status> statuses = statusService.getAllByCypher(cypher);
        List<Team> teams = teamService.getAll();
        Map<Long, CypherStatus> map = new HashMap<>();
        teams.forEach(team -> {
                statuses.forEach(status -> {
                    if ((status.getTeam() == team)) {
                        map.put(team.getTeamId(), status.getCypherStatus());
                    }
                });
        });
        return map;
    }
}
