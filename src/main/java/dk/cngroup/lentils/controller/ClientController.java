package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/client")
public class ClientController {

    private final static String CLIENT_VIEW_CYPHER_LIST = "clientSide/clientSideList";
    private final static String CLIENT_VIEW_CYPHER_DESCRIPTION = "clientSide/clientSideCypher";

    private CypherService cypherService;
    private TeamService teamService;
    private StatusService statusService;
    private HintService hintService;

    @Autowired
    public ClientController(CypherService cypherService,
                            TeamService teamService,
                            StatusService statusService,
                            HintService hintService) {
        this.cypherService = cypherService;
        this.teamService = teamService;
        this.statusService = statusService;
        this.hintService = hintService;
    }

    @GetMapping(value = "/list")
    public String listAllCyphers(Model model) {
        model.addAttribute("cyphers", cypherService.getAll());
        model.addAttribute("team", new Team("Developeri",4));
        model.addAttribute("hints", hintService.getAll());
        model.addAttribute("status", CypherStatus.SKIPPED);
        return CLIENT_VIEW_CYPHER_LIST;
    }

    @GetMapping(value = "/cypherDetail/{id}")
    public String cypherDetail(@PathVariable("id")Long id, Model model) {
        Cypher cypher = cypherService.getCypher(id);
        model.addAttribute("team",new Team("devel",5));
        model.addAttribute("cypher",cypher);
        return CLIENT_VIEW_CYPHER_DESCRIPTION;
    }
}
