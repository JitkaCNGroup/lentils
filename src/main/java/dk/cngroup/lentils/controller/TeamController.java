package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.TeamService;
import dk.cngroup.lentils.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/team")
public class TeamController {
    private static final String VIEW_PATH = "team/main";
    private static final String REDIRECT_TO_MAIN_VIEW = "redirect:/admin/team";
    private static final String ACTION_TEAM_SAVE = "/admin/team/add";
    private static final String ACTION_TEAM_UPDATE = "/admin/team/update/";

    private final TeamService teamService;
    private final UserService userService;

    public TeamController(final TeamService teamService,
                          final UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping
    public String addTeam(final Model model) {
        fillModelAttributes(model, teamService.getAll(), new Team(), ACTION_TEAM_SAVE);
        return VIEW_PATH;
    }

    @PostMapping(value = "/add")
    public String addTeam(
            @Valid @ModelAttribute final Team team,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (bindingResult.hasErrors()) {
            fillModelAttributes(model, teamService.getAll(), team, ACTION_TEAM_SAVE);
            return VIEW_PATH;
        }
        teamService.save(team);
        userService.createUserForTeam(team);
        return REDIRECT_TO_MAIN_VIEW;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") final Long id, final Model model) {
        fillModelAttributes(model, teamService.getAll(), teamService.getTeam(id), ACTION_TEAM_UPDATE + id);
        return VIEW_PATH;
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable("id") final Long id,
            @Valid final Team team,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (bindingResult.hasErrors()) {
            fillModelAttributes(model, teamService.getAll(), team, ACTION_TEAM_SAVE);
            return VIEW_PATH;
        }
        teamService.save(team);
        userService.createUserForTeam(team);
        return REDIRECT_TO_MAIN_VIEW;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") final Long id) {
        teamService.delete(id);
        return REDIRECT_TO_MAIN_VIEW;
    }

    private void fillModelAttributes(
            final Model model,
            final List<Team> teams,
            final Team team,
            final String action
    ) {
        model.addAttribute("teams", teams);
        model.addAttribute("team", team);
        model.addAttribute("action", action);
    }
}
