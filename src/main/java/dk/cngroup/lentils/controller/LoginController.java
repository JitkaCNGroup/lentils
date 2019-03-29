package dk.cngroup.lentils.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
    @GetMapping("/successfulLogin")
    public String redirectUserBasedOnRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toString();

        if (role.contains("ADMIN")) {
            return "redirect:/admin/team"; // TODO : Martin - admin list of actions
        } else if (role.contains("ORGANIZER")) {
            return "redirect:/game/progress";
        } else if (role.contains("USER")) {
            return "redirect:/user"; // TODO : Pavel - user view
        } else {
            return "login/login";
        }
    }
}
