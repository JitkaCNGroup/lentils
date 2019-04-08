package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.factory.LoginFactory;
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
        return LoginFactory.getRedirectUrlAfterLoginForRole(authentication.getAuthorities().toString());
    }
}
