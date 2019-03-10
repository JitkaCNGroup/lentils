package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.ProgressService;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/progress")
public class ProgressController {
    private static final String PROGRESS_STAGE = "progress/stage";
    private static final String PROGRESS_LIST = "progress/list";

    private final TeamService teamService;
    private final CypherService cypherService;
    private final ProgressService progressService;

    @Autowired
    public ProgressController(TeamService teamService,
                              CypherService cypherService,
                              ProgressService progressService) {
        this.teamService = teamService;
        this.cypherService = cypherService;
        this.progressService = progressService;
    }

    @GetMapping
    public String listProgress(Model model) {
        model.addAttribute("cyphers", cypherService.getAll());
        return PROGRESS_LIST;
    }

    @GetMapping(value = "/stage")
    public String stageProgress(@RequestParam("cypherId") Long cypherId, Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        fillModelAttributes(model, teamService.getAll(), cypher);
        model.addAttribute("map", progressService.getProgressStatusMap(cypher));
        return PROGRESS_STAGE;
    }

    @GetMapping(value = "/skip/{cypherId}")
    public String stageSkip (@PathVariable("cypherId") Long cypherId, @RequestParam("teamId") Long teamId, Model model){
        Cypher cypher = cypherService.getCypher(cypherId);
        progressService.setSkipProgressStatus(cypherService.getCypher(cypherId), teamService.getTeam(teamId));
        fillModelAttributes(model, teamService.getAll(), cypherService.getCypher(cypherId));
        model.addAttribute("map", progressService.getProgressStatusMap(cypher));
        return PROGRESS_STAGE;
    }

    @GetMapping(value = "/pass/{cypherId}")
    public String stagePass (@PathVariable("cypherId") Long cypherId, @RequestParam("teamId") Long teamId, Model model){
        Cypher cypher = cypherService.getCypher(cypherId);
        progressService.setPassProgressStatus(cypherService.getCypher(cypherId), teamService.getTeam(teamId));
        fillModelAttributes(model, teamService.getAll(), cypherService.getCypher(cypherId));
        model.addAttribute("map", progressService.getProgressStatusMap(cypher));
        return PROGRESS_STAGE;
    }

    @GetMapping(value = "/pending/{cypherId}")
    public String stagePending (@PathVariable("cypherId") Long cypherId, @RequestParam("teamId") Long teamId, Model model){
        Cypher cypher = cypherService.getCypher(cypherId);
        progressService.setPendingProgressStatus(cypherService.getCypher(cypherId), teamService.getTeam(teamId));
        fillModelAttributes(model, teamService.getAll(), cypherService.getCypher(cypherId));
        model.addAttribute("map", progressService.getProgressStatusMap(cypher));
        return PROGRESS_STAGE;
    }

    private void fillModelAttributes(Model model, List<Team> teams, Cypher cypher) {
        model.addAttribute("teams", teams);
        model.addAttribute("cypher", cypher);
    }
}
