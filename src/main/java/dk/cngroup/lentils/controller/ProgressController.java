package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/progress")
public class ProgressController {
    private static final String PROGRESS_STAGE = "progress/stage";
    private static final String PROGRESS_PASS = "progress/pass";
    private static final String PROGRESS_SKIP = "progress/skip";

    private final TeamService teamService;
    private final CypherService cypherService;

    public ProgressController(TeamService teamService, CypherService cypherService) {
        this.teamService = teamService;
        this.cypherService = cypherService;
    }

    @GetMapping
    public String stageProgress (@RequestParam("cypherId")Long cypherId, Model model){
        Cypher cypher = cypherService.getCypher(cypherId);
        fillModelAttributes(model, teamService.getAll(), cypher);
        return PROGRESS_STAGE;
    }

    @GetMapping("/skip")
    public String stageSkip (){
        return PROGRESS_SKIP;
    }

    @GetMapping("/pass")
    public String stagePass (){
        return PROGRESS_PASS;
    }

    private void fillModelAttributes(Model model, List<Team> teams, Cypher cypher) {
        model.addAttribute("teams", teams);
        model.addAttribute("cypher", cypher);
    }
}
