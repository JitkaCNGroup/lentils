package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.dto.TeamFormDTO;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.TeamService;
import dk.cngroup.lentils.service.convertors.ModelMapperWrapper;
import dk.cngroup.lentils.service.convertors.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/team")
public class TeamController {
    private static final String VIEW_ADMIN_TEAM_MAIN = "team/main";
    private static final String REDIRECT_ADMIN_TEAM = "redirect:/admin/team";
    private static final String ACTION_TEAM_SAVE = "/admin/team/add";
    private static final String ACTION_TEAM_UPDATE = "/admin/team/update/";

    private final TeamService teamService;
    private final ObjectMapper mapper;

    @Autowired
    public TeamController(final TeamService teamService,
                          final ModelMapperWrapper modelMapperWrapper) {
        this.teamService = teamService;
        this.mapper = modelMapperWrapper;
    }

    @GetMapping
    public String addTeam(final Model model) {
        fillModelAttributes(model, teamService.getAll(), new TeamFormDTO(), ACTION_TEAM_SAVE);
        return VIEW_ADMIN_TEAM_MAIN;
    }

    @PostMapping(value = "/add")
    public String addTeam(@Valid @ModelAttribute("team") final TeamFormDTO teamFormDto,
            final BindingResult bindingResult,
            final Model model) {
        Team team = new Team();
        mapper.map(teamFormDto, team);
        teamService.checkUsernameIsUnique(team, bindingResult);
        if (bindingResult.hasErrors()) {
            fillModelAttributes(model, teamService.getAll(), teamFormDto, ACTION_TEAM_SAVE);
            return VIEW_ADMIN_TEAM_MAIN;
        }
        teamService.save(team);
        return REDIRECT_ADMIN_TEAM;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") final Long id, final Model model) {
        fillModelAttributes(model,
                teamService.getAll(),
                mapper.map(teamService.getTeam(id), TeamFormDTO.class),
                ACTION_TEAM_UPDATE + id);
        return VIEW_ADMIN_TEAM_MAIN;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") final Long id,
            @Valid @ModelAttribute("team") final TeamFormDTO teamFormDto,
            final BindingResult bindingResult,
            final Model model) {
        Team team = teamService.getTeam(id);
        mapper.map(teamFormDto, team);
        teamService.checkUsernameIsUnique(team, bindingResult);
        if (bindingResult.hasErrors()) {
            fillModelAttributes(model, teamService.getAll(), teamFormDto, ACTION_TEAM_UPDATE + id);
            return VIEW_ADMIN_TEAM_MAIN;
        }
        teamService.update(team);
        return REDIRECT_ADMIN_TEAM;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") final Long id) {
        teamService.delete(id);
        return REDIRECT_ADMIN_TEAM;
    }

    private void fillModelAttributes(
            final Model model,
            final List<Team> teams,
            final TeamFormDTO teamFormDto,
            final String action
    ) {
        model.addAttribute("teams", teams);
        model.addAttribute("teamFormDto", teamFormDto);
        model.addAttribute("action", action);
    }
}
