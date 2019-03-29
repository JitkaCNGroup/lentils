package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final static String CLIENT_VIEW_CYPHER_LIST = "client/list";
    private final static String CLIENT_VIEW_CYPHER_DESCRIPTION = "client/detail";
    private final static String REDIRECT_CLIENT_VIEW_CYPHER_LIST = "redirect:/client/list";
    private final static String REDIRECT_CLIENT_VIEW_CYPHER_DESCRIPTION = "redirect:/client/detail";

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
        model.addAttribute("hintsNotTaken",
                hintService.getHintsNotTakenByTeam(cypher,
                        teamService.getTeam(2L)));
        model.addAttribute("hintsTaken",
                hintTakenService.getAllByTeamAndCypher(teamService.getTeam(2L), cypher));
        model.addAttribute("nextCypher", cypherService.getNext(cypher.getStage()));
        return CLIENT_VIEW_CYPHER_DESCRIPTION;
    }

    @PostMapping(value = "/detail/verify")
    public String verifyCodeword(Cypher cypher, Model model) {
        model.addAttribute("cypher", cypher);
        if(cypherService.checkCodeword(cypher.getCodeword(), cypher.getCypherId())) {
            statusService.markCypher(cypherService.getCypher(cypher.getCypherId()),
                    teamService.getTeam(2L),
                    CypherStatus.SOLVED);
        }
        return REDIRECT_CLIENT_VIEW_CYPHER_DESCRIPTION + "/" + cypher.getCypherId();
    }

    @RequestMapping(value = "/detail/takeHint/{hintId}")
    public String getHint(@PathVariable("hintId")Long id, Cypher cypher, Model model) {
        model.addAttribute(cypher);
        hintTakenService.takeHint(teamService.getTeam(2L),
                hintService.getHint(id));
        return REDIRECT_CLIENT_VIEW_CYPHER_DESCRIPTION + "/" + cypher.getCypherId();
    }

    @PostMapping(value = "/detail/giveUp")
    public String skipCypher(Cypher cypher, Model model) {
        model.addAttribute("cypher", cypher);
        if(statusService.getStatusName(teamService.getTeam(2L),
                cypherService.getCypher(cypher.getCypherId())).equals("PENDING")) {
            statusService.markCypher(cypherService.getCypher(cypher.getCypherId()),
                    teamService.getTeam(2L),
                    CypherStatus.SKIPPED);
        }
        return REDIRECT_CLIENT_VIEW_CYPHER_LIST;
    }
}
