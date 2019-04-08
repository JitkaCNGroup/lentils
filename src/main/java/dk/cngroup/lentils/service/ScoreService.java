package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    private final TeamService teamService;
    private final StatusService statusService;
    private final HintTakenService hintTakenService;

    @Autowired
    public ScoreService(TeamService teamService, StatusService statusService, HintTakenService hintTakenService) {
        this.teamService = teamService;
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
    }

    private int getScoreByTeam(Team team) {

        return 0;
    }
}
