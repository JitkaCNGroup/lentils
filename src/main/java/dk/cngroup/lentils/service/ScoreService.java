package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScoreService {

    private final StatusService statusService;
    private final HintTakenService hintTakenService;
    private final CypherService cypherService;

    @Autowired
    public ScoreService(final StatusService statusService,
                        final HintTakenService hintTakenService,
                        final CypherService cypherService) {
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
        this.cypherService = cypherService;
    }

    public int getScoreByTeamAndCypher(final Team team, final Cypher cypher) {
        int hintScore = hintTakenService.getHintScore(team, cypher);
        int statusScore = statusService.getStatusScore(team, cypher);
        return (statusScore - hintScore);
    }

    public int getScoreByTeam(final Team team) {
        int teamScore = 0;
        final List<Cypher> cyphers = cypherService.getAll();
        for (Cypher cypher: cyphers) {
            teamScore += getScoreByTeamAndCypher(team, cypher);
        }
        return teamScore;
    }
}
