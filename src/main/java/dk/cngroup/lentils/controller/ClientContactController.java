package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Contact;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.ContactService;
import dk.cngroup.lentils.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/contact")
public class ClientContactController {
    private static final String VIEW_CONTACT_FORM = "clientcontact/list";
    private final ContactService contactService;
    private final ScoreService scoreService;

    @Autowired
    public ClientContactController(final ContactService contactService, final ScoreService scoreService) {
        this.contactService = contactService;
        this.scoreService = scoreService;
    }

    @GetMapping
    public String contact(@AuthenticationPrincipal final CustomUserDetails user,
                          final Model model) {
        model.addAttribute("contact", contactService.getContact());
        model.addAttribute("team", user.getTeam());
        model.addAttribute("score", scoreService.getScoreByTeam(user.getTeam()));
        return VIEW_CONTACT_FORM;
    }
}
