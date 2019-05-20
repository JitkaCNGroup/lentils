package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.view.TeamScore;
import dk.cngroup.lentils.entity.view.TeamScoreDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ScoreService {

    private final StatusService statusService;
    private final HintTakenService hintTakenService;
    private final CypherService cypherService;
    private final TeamService teamService;
    private final RankService rankService;

    @Autowired
    public ScoreService(final StatusService statusService,
                        final HintTakenService hintTakenService,
                        final CypherService cypherService,
                        final TeamService teamService,
                        final RankService rankService) {
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
        this.cypherService = cypherService;
        this.teamService = teamService;
        this.rankService = rankService;
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

    public List<TeamScore> getAllTeamsWithScores() {
        List<Team> teams = teamService.getAll();
        List<TeamScore> teamScores = teams.stream()
                .map(team -> new TeamScore(team, this.getScoreByTeam(team)))
                .sorted()
                .collect(Collectors.toList());
        rankService.computeRank(teamScores);
        return teamScores;
    }

    public List<TeamScoreDetail> getTeamWithDetailScores(final Team team) {
        List<TeamScoreDetail> teamScoreDetails = new ArrayList<>();
        final List<Cypher> cyphers = cypherService.getAll();
        for (Cypher cypher: cyphers) {
            TeamScoreDetail teamScoreDetail = new TeamScoreDetail(
                    cypher,
                    statusService.getStatusScore(team, cypher),
                    hintTakenService.getAllByTeamAndCypher(team, cypher),
                    hintTakenService.getHintScore(team, cypher),
                    this.getScoreByTeamAndCypher(team, cypher));
            teamScoreDetails.add(teamScoreDetail);
        }
        return teamScoreDetails;
    }
}
