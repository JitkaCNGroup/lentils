package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

@Controller
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    private final String VIEW_PATH = "team/list";
    private final String REDIRECT = "redirect:/team/add";
    private final String ACTION_TEAM_ADD = "/team/add";
    private final String ACTION_TEAM_UPDATE = "/team/update/";

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(value = "/add")
    public String addTeam(Model model) {
        fillModelAttributes(model, teamService.getAll(), new Team(), ACTION_TEAM_ADD);
        return VIEW_PATH ;
    }

    @PostMapping(value = "/add")
    public String addTeam(@Valid Team team, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            fillModelAttributes(model, teamService.getAll(), new Team(), ACTION_TEAM_ADD);
            return VIEW_PATH ;
        }
        teamService.add(team);
        return REDIRECT;
    }

    @ExceptionHandler(InputMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNotFoundException(InputMismatchException e)
    {
        return new Error(e.getMessage());
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        fillModelAttributes(model, teamService.getAll(), teamService.get(id).get(), ACTION_TEAM_UPDATE + id);
        return VIEW_PATH;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid Team team, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            fillModelAttributes(model, teamService.getAll(), teamService.get(id).get(), ACTION_TEAM_ADD);
            return VIEW_PATH ;
        }
        teamService.save(team);
        return REDIRECT;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        teamService.delete(id);
        return REDIRECT;
    }

    private void fillModelAttributes(Model model, List<Team> teams, Team team, String action) {
        model.addAttribute("teams", teams);
        model.addAttribute("team", team);
        model.addAttribute("action", action);
    }
}
