package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.HintTakenService;
import dk.cngroup.lentils.service.StatusService;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ClientController {

    private static final String CLIENT_VIEW_CYPHER_LIST = "client/cypher/list";
    private static final String CLIENT_VIEW_CYPHER_DETAIL = "client/cypher/detail";
    private static final String CLIENT_VIEW_HINT_LIST = "client/hint/list";
    private static final String REDIRECT_TO_CLIENT_CYPHER_DETAIL = "redirect:/cypher/";

    private CypherService cypherService;
    private TeamService teamService;
    private StatusService statusService;
    private HintService hintService;
    private HintTakenService hintTakenService;

    @Autowired
    public ClientController(final CypherService cypherService,
                            final TeamService teamService,
                            final StatusService statusService,
                            final HintService hintService,
                            final HintTakenService hintTakenService) {
        this.cypherService = cypherService;
        this.teamService = teamService;
        this.statusService = statusService;
        this.hintService = hintService;
        this.hintTakenService = hintTakenService;
    }

    @GetMapping(value = "cypher/{id}")
    public String cypherDetail(@PathVariable("id") final Long id, final Model model) {
        Cypher cypher = cypherService.getCypher(id);
        String status = statusService.getStatusNameByTeamAndCypher(teamService.getTeam(2L), cypher);
        model.addAttribute("team", teamService.getTeam(2L));
        model.addAttribute("cypher", cypher);
        model.addAttribute("status", status);
        model.addAttribute("hintsTaken",
                hintTakenService.getAllByTeamAndCypher(teamService.getTeam(2L), cypher));
        model.addAttribute("nextCypher", cypherService.getNext(cypher.getStage()));
        return CLIENT_VIEW_CYPHER_DETAIL;
    }

    @GetMapping(value = "hint")
    public String listAllAvailableHintsForCypher(final Cypher cypher, final Model model) {
        model.addAttribute("cypher", cypher);
        return CLIENT_VIEW_HINT_LIST;
    }

    @PostMapping(value = "cypher/verify")
    public String verifyCodeword(final Cypher cypher) {
        if (cypherService.checkCodeword(cypher.getCodeword(), cypher.getStage())) {
            statusService.markCypher(cypherService.getCypher(cypher.getCypherId()),
                    teamService.getTeam(2L),
                    CypherStatus.SOLVED);
        }
        return REDIRECT_TO_CLIENT_CYPHER_DETAIL + cypher.getCypherId();
    }

    @PostMapping(value = "cypher/takeHint/{hintId}")
    public String getHint(@PathVariable("hintId") final Long id, final Cypher cypher) {
        hintTakenService.takeHint(teamService.getTeam(2L),
                hintService.getHint(id));
        return REDIRECT_TO_CLIENT_CYPHER_DETAIL + cypher.getCypherId();
    }

    @PostMapping(value = "cypher/giveUp")
    public String skipCypher(final Cypher cypher) {
        if (statusService.getStatusNameByTeamAndCypher(teamService.getTeam(2L),
                cypherService.getCypher(cypher.getCypherId())).equals("PENDING")) {
            statusService.markCypher(cypherService.getCypher(cypher.getCypherId()),
                    teamService.getTeam(2L),
                    CypherStatus.SKIPPED);
        }
        return REDIRECT_TO_CLIENT_CYPHER_DETAIL + cypherService.getNext(cypher.getStage()).getCypherId();
    }
}
