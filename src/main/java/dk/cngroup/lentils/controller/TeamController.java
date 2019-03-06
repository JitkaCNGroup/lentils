package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/team")
public class TeamController {
    private static final String VIEW_PATH = "team/main";
    private static final String REDIRECT_TO_MAIN_VIEW = "redirect:/team";
    private static final String ACTION_TEAM_SAVE = "/team/add";
    private static final String ACTION_TEAM_UPDATE = "/team/update/";

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(value = "")
    public String addTeam(Model model) {
        fillModelAttributes(model, teamService.getAll(), new Team(), ACTION_TEAM_SAVE);
        return VIEW_PATH ;
    }

    @PostMapping(value = "/add")
    public String addTeam(@Valid @ModelAttribute Team team, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            fillModelAttributes(model, teamService.getAll(), new Team(), ACTION_TEAM_SAVE);
            return REDIRECT_TO_MAIN_VIEW;
        }
        teamService.save(team);
        return REDIRECT_TO_MAIN_VIEW;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        fillModelAttributes(model, teamService.getAll(), teamService.getTeam(id), ACTION_TEAM_UPDATE + id);
        return VIEW_PATH;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid Team team, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            fillModelAttributes(model, teamService.getAll(), team, ACTION_TEAM_SAVE);
            return VIEW_PATH ;
        }
        teamService.save(team);
        return REDIRECT_TO_MAIN_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        teamService.delete(id);
        return REDIRECT_TO_MAIN_VIEW;
    }

    private void fillModelAttributes(Model model, List<Team> teams, Team team, String action) {
        model.addAttribute("teams", teams);
        model.addAttribute("team", team);
        model.addAttribute("action", action);
    }
}
