package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScoreService {

    private final TeamService teamService;
    private final StatusService statusService;
    private final HintTakenService hintTakenService;
    private final CypherService cypherService;

    @Autowired
    public ScoreService(TeamService teamService, StatusService statusService, HintTakenService hintTakenService, CypherService cypherService) {
        this.teamService = teamService;
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
        this.cypherService = cypherService;
    }

    public int getScoreByTeamAndCypher(Team team, Cypher cypher) {

        int hintScore = hintTakenService.getHintScore(team, cypher);
        int statusScore = statusService.getStatusScore(team, cypher);
        return (statusScore - hintScore);
    }

    public int getScoreByTeam(Team team) {

        int teamScore = 0;
        int scoreByCypher;
        final List<Cypher> cyphers = cypherService.getAll();
        for (Cypher cypher: cyphers) {
            scoreByCypher = getScoreByTeamAndCypher(team, cypher);
            teamScore += scoreByCypher;
        }
        return teamScore;
    }
}
