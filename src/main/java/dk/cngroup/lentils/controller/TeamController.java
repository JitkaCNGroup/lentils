package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/team")//http://localhost:8080/team/add
public class TeamController {
    private final TeamService teamService;

    private final String VIEW_PATH = "team/";

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @RequestMapping(value = "/add", method = GET)
    public String addTeam(Model model) {
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("team", new Team());
        model.addAttribute("action", "/team/add");
        return VIEW_PATH + "list";
    }

    @RequestMapping(value = "/add", method = POST)
    public String addTeam(@Valid Team team, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.getAll());
            model.addAttribute("team", new Team());
            model.addAttribute("action", "/team/add");
            return VIEW_PATH + "list";
        } else {
            teamService.add(team);
            return "redirect:/team/add";
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("team", teamService.get(id).get());
        model.addAttribute("action", "/team/update/" + id);

        return VIEW_PATH + "list";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid Team team, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.getAll());
            model.addAttribute("team", teamService.get(id).get());
            model.addAttribute("action", "/team/add");
            return VIEW_PATH + "list";
        } else {
            teamService.save(team);
            return "redirect:/team/add";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        teamService.delete(id);
        return "redirect:/team/add";
    }
}
