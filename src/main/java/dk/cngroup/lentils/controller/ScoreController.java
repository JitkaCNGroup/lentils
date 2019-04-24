package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.view.TeamScoreDetail;
import dk.cngroup.lentils.service.ScoreService;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/game/score")
public class ScoreController {
    private static final String SCORE_LIST = "score/list";
    private static final String SCORE_LIST_DETAIL = "score/team";
    private static final String ERROR = "error/error";

    private final TeamService teamService;
    private final ScoreService scoreService;

    @Autowired
    public ScoreController(final TeamService teamService,
                           final ScoreService scoreService) {
        this.teamService = teamService;
        this.scoreService = scoreService;
    }

    @GetMapping
    public String listScore(final Model model) {
        model.addAttribute("teamsWithScores", scoreService.getAllTeamsWithScores());
        return SCORE_LIST;
    }

    @GetMapping(value = "/{teamId}")
    public String viewDetailScoreForTeam(final @PathVariable("teamId") Long teamId,
                                     final Model model) {
        Team team = teamService.getTeam(teamId);
        List<TeamScoreDetail> teamScoreDetails = scoreService.getTeamWithDetailScores(team);
        model.addAttribute("team", team);
        model.addAttribute("teamWithDetailScores", teamScoreDetails);
        return SCORE_LIST_DETAIL;
    }
}
