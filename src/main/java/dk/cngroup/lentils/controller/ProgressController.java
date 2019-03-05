package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/progress")
public class ProgressController {
    private static final String PROGRESS_STAGE = "progress/stage";
    private static final String PROGRESS_PASS = "progress/pass";
    private static final String PROGRESS_SKIP = "progress/skip";

    private final TeamService teamService;

    public ProgressController(TeamService teamService) {
        this.teamService = teamService;
    }

    // v budoucnu to bude progress/stage/{id}
    @GetMapping("/stage")
    public String stageProgress (Model model){
        fillModelAttributes(model, teamService.getAll());
        return PROGRESS_STAGE;
    }

    // pro skip a pass by mohla byt v budoucnu vytvorena nova class: progress/stage/ID/pass||skip
    @GetMapping("/skip")
    public String stageSkip (Model model){
        fillModelAttributes(model, teamService.getAll());
        return PROGRESS_SKIP;
    }

    @GetMapping("/pass")
    public String stagePass (Model model){
        fillModelAttributes(model, teamService.getAll());
        return PROGRESS_PASS;
    }

    private void fillModelAttributes(Model model, List<Team> teams) {
        model.addAttribute("teams", teams);
    }
}
