package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/client")
public class ClientController {

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


    //temporary team(will be team logged in), cypherstatus
    @GetMapping(value = "/list")
    public String listAllCyphers(Model model){
        model.addAttribute("cyphers", cypherService.getAll());
        model.addAttribute("team", new Team("Developeri",4));
        model.addAttribute("hints", hintService.getAll());
        model.addAttribute("status", CypherStatus.SKIPPED);
        return "clientSide/clientSideList";
    }
}
