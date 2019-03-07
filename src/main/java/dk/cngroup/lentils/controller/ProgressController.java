package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.StatusService;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/progress")
public class ProgressController {
    private static final String PROGRESS_STAGE = "progress/stage";
    private static final String PROGRESS_PASS = "progress/pass";
    private static final String PROGRESS_SKIP = "progress/skip";

    private final TeamService teamService;
    private final CypherService cypherService;
    private final StatusService statusService;

    public ProgressController(TeamService teamService, CypherService cypherService, StatusService statusService) {
        this.teamService = teamService;
        this.cypherService = cypherService;
        this.statusService = statusService;
    }

    @GetMapping
    public String stageProgress (@RequestParam("cypherId")Long cypherId, Model model){
        Cypher cypher = cypherService.getCypher(cypherId);
        fillModelAttributes(model, teamService.getAll(), cypher);
        return PROGRESS_STAGE;
    }

    @GetMapping("/skip/{cypherId}")
    public @ResponseBody
    String stageSkip (@PathVariable("cypherId") Long cypherId, @RequestParam("teamId") Long teamId, Model model){
        Cypher cypher = cypherService.getCypher(cypherId);
        Team team = teamService.getTeam(teamId);
        statusService.markCypherSolvedForTeam(cypher, team);
        model.addAttribute("status", statusService.getStatusForTeam(cypher, team));
        fillModelAttributes(model, teamService.getAll(), cypher);
        return PROGRESS_STAGE;
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
