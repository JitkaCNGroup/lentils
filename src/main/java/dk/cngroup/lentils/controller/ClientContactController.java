package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.ContactService;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.ScoreService;
import dk.cngroup.lentils.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/contact")
public class ClientContactController {
    private static final String VIEW_CONTACT_LIST = "clientcontact/list";
    private final ContactService contactService;
    private final ScoreService scoreService;
    private final CypherService cypherService;
    private final StatusService statusService;

    @Autowired
    public ClientContactController(final ContactService contactService,
                                   final ScoreService scoreService,
                                   final CypherService cypherService,
                                   final StatusService statusService) {
        this.contactService = contactService;
        this.scoreService = scoreService;
        this.cypherService = cypherService;
        this.statusService = statusService;
    }

    @GetMapping
    public String contact(@AuthenticationPrincipal final CustomUserDetails user,
                          final Model model) {
        List<Cypher> cyphers = cypherService.getAllCyphersOrderByStageAsc();
        if (statusService.isStatusInDbByCypherAndTeam(cyphers.get(0), user.getTeam())) {
            model.addAttribute("gameStarted", true);
            model.addAttribute("contact", contactService.getContact());
            model.addAttribute("team", user.getTeam());
            model.addAttribute("score", scoreService.getScoreByTeam(user.getTeam()));
        }
        return VIEW_CONTACT_LIST;
    }
}
