package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.factory.CypherStatusFactory;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.ProgressService;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@RequestMapping("/progress")
public class ProgressController {
    private static final String PROGRESS_STAGE = "progress/stage";
    private static final String PROGRESS_LIST = "progress/list";
    private static final String ERROR = "error/error";

    private final TeamService teamService;
    private final CypherService cypherService;
    private final ProgressService progressService;

    @Autowired
    public ProgressController(final TeamService teamService,
                              final CypherService cypherService,
                              final ProgressService progressService) {
        this.teamService = teamService;
        this.cypherService = cypherService;
        this.progressService = progressService;
    }

    @GetMapping
    public String listProgress(final Model model) {
        model.addAttribute("cyphers", cypherService.getAll());
        return PROGRESS_LIST;
    }

    @GetMapping(value = "/stage")
    public String stageProgress(final @RequestParam("cypherId") Long cypherId, final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        model.addAttribute("cypher", cypher);
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("teamsStatuses", progressService.getTeamsStatuses(cypher));
        return PROGRESS_STAGE;
    }

    @GetMapping(value = "/changeStatus/{cypherId}")
    public String changeCypherStatus(final @PathVariable("cypherId") Long cypherId,
                                      final @RequestParam("teamId") Long teamId,
                                      final @RequestParam("newStatus") String newStatus,
                                      final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        Team team = teamService.getTeam(teamId);
        CypherStatus cypherStatus = CypherStatusFactory.create(newStatus);
        progressService.makeCypher(cypher, team, cypherStatus);

        model.addAttribute("cypher", cypher);
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("teamsStatuses", progressService.getTeamsStatuses(cypher));
        return PROGRESS_STAGE;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleError() {
        return ERROR;
    }
}
