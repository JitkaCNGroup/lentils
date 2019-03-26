package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final static String CLIENT_VIEW_CYPHER_LIST = "client/list";
    private final static String CLIENT_VIEW_CYPHER_DESCRIPTION = "client/detail";
    private final static String REDIRECT_CLIENT_VIEW_CYPHER_LIST = "redirect:/client/list";

    private CypherService cypherService;
    private TeamService teamService;
    private StatusService statusService;
    private HintService hintService;
    private HintTakenService hintTakenService;

    @Autowired
    public ClientController(CypherService cypherService,
                            TeamService teamService,
                            StatusService statusService,
                            HintService hintService,
                            HintTakenService hintTakenService) {
        this.cypherService = cypherService;
        this.teamService = teamService;
        this.statusService = statusService;
        this.hintService = hintService;
        this.hintTakenService = hintTakenService;
    }

    @GetMapping(value = "/list")
    public String listAllCyphers(Model model) {
        model.addAttribute("cyphers", cypherService.getAll());
        model.addAttribute("team", teamService.getTeam(2L));
        model.addAttribute("hints", hintService.getAll());
        model.addAttribute("status", CypherStatus.SKIPPED);
        return CLIENT_VIEW_CYPHER_LIST;
    }

    @GetMapping(value = "/detail/{id}")
    public String cypherDetail(@PathVariable("id")Long id, Model model) {
        Cypher cypher = cypherService.getCypher(id);
        String status = statusService.getStatusName(teamService.getTeam(2L), cypher);
        model.addAttribute("team", teamService.getTeam(2L));
        model.addAttribute("cypher",cypher);
        model.addAttribute("statusName", status);
        model.addAttribute("hints", hintService.getAllByCypher(cypher));
        model.addAttribute("hintsTaken", hintTakenService.getAllByCypher(cypher));
        return CLIENT_VIEW_CYPHER_DESCRIPTION;
    }

    @PostMapping(value = "/detail/{id}/verify")
    public String verifyCodeword(@PathVariable("id") Long id,
                                 @RequestParam String codeWord) {
        boolean goodCodeword = cypherService.checkCodeword(codeWord, id);
        if(goodCodeword) {
            statusService.markCypher(cypherService.getCypher(id),
                    teamService.getTeam(2L),CypherStatus.SOLVED);
            return CLIENT_VIEW_CYPHER_LIST;
        }
        else {
            return CLIENT_VIEW_CYPHER_LIST;
        }
    }

    @GetMapping(value = "/detail/takeHint/{hintId}")
    public String getHint(@PathVariable("hintId") Long hintId) {
        hintTakenService.takeHint(teamService.getTeam(2L),
                hintService.getHint(hintId));
        return CLIENT_VIEW_CYPHER_DESCRIPTION;
    }

    @PostMapping(value = "/detail/giveUp")
    public String skipCypher(Cypher cypher, Model model) {
        model.addAttribute("cypher", cypher);
        if(statusService.getStatusName(teamService.findById(2L),
                cypherService.getCypher(cypher.getCypherId())).equals("PENDING")) {
            statusService.markCypherSkippedForTeam(cypherService.getCypher(cypher.getCypherId()),
                    teamService.findById(2L));
        }
        return REDIRECT_CLIENT_VIEW_CYPHER_LIST;
    }
}
