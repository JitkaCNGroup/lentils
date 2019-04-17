package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.factory.CypherStatusFactory;
import dk.cngroup.lentils.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game/score")
public class ScoreController {
    private static final String PROGRESS_STAGE = "progress/stage";
    private static final String SCORE_LIST = "score/list";
    private static final String ERROR = "error/error";
    private static final String HINT_LIST = "progress/getHintList";

    private final TeamService teamService;
    private final CypherService cypherService;
    private final ProgressService progressService;
    private final HintService hintService;
    private final HintTakenService hintTakenService;
    private final ScoreService scoreService;

    @Autowired
    public ScoreController(final TeamService teamService,
                           final CypherService cypherService,
                           final ProgressService progressService,
                           final HintService hintService,
                           final HintTakenService hintTakenService,
                           final ScoreService scoreService) {
        this.teamService = teamService;
        this.cypherService = cypherService;
        this.progressService = progressService;
        this.hintService = hintService;
        this.hintTakenService = hintTakenService;
        this.scoreService = scoreService;
    }

    @GetMapping
    public String listScore(final Model model) {
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("teamsScores", scoreService.getAllTeamsWithScores());
        return SCORE_LIST;
    }
    

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleError() {
        return ERROR;
    }
}
