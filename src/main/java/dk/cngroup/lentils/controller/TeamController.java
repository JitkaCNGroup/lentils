package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/team")
public class TeamController
{
	private final TeamService teamService;
	private final String VIEW_PATH = "team/";

	public TeamController(TeamService teamService)
	{
		this.teamService = teamService;
	}

	@GetMapping("/add")
	public ModelAndView addTeam()
	{
		List<Team> teams = teamService.getAll();
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "team_list", "teams", teams);
		modelAndView.addObject("team", new Team());
		modelAndView.addObject("action", "/team/add");
		return modelAndView;
	}

	@PostMapping("/add")
	public ModelAndView addTeam(@Valid Team team,
								BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "team_list");
			modelAndView.addObject("teams", teamService.getAll());
			modelAndView.addObject("action", "/team/add");
			return modelAndView;
		} else
		{
			// add new
			teamService.add(team);
			return new ModelAndView("redirect:/team/add");
		}
	}

	@GetMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Long id)
	{
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "team_list");
		Optional<Team> team = teamService.get(id);
		modelAndView.addObject("teams", teamService.getAll());
		modelAndView.addObject("team", team.get());
		modelAndView.addObject("action", "/team/update/" + id);
		return modelAndView;
	}

	@PostMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Long id, @Valid Team team,
							   BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "team_list");
			modelAndView.addObject("teams", teamService.getAll());
			team.setId(null);
			modelAndView.addObject("team", team);
			modelAndView.addObject("action", "/team/add");
			return modelAndView;
		} else
		{
			teamService.update(id, team);
			return new ModelAndView("redirect:/team/add");
		}
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id)
	{
		Team team = teamService.get(id).get();
		teamService.delete(team);
		return new ModelAndView("redirect:/team/add");
	}
}
