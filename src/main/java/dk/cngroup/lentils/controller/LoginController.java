package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.util.LoginUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return LoginUtils.getRedirectUrlAfterLoginForRole(authentication.getAuthorities());
        }
        return "login/login";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/successfulLogin")
    public String redirectUserBasedOnRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return LoginUtils.getRedirectUrlAfterLoginForRole(authentication.getAuthorities());
    }
}
