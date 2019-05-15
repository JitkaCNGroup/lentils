package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.TeamProgressWithTeam;
import dk.cngroup.lentils.factory.CypherStatusFactory;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.GameLogicService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.HintTakenService;
import dk.cngroup.lentils.service.ProgressService;
import dk.cngroup.lentils.service.StatusService;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/game/progress")
public class ProgressController {
    private static final String PROGRESS_STAGE = "progress/stage";
    private static final String PROGRESS_LIST = "progress/list";
    private static final String TEAM_LIST = "progress/teamList";
    private static final String REDIRECT_TEAM_LIST = "redirect:/game/progress/teamsList";
    private static final String TEAM_DETAIL = "progress/teamDetail";
    private static final String ERROR = "error/error";
    private static final String HINT_LIST = "progress/getHintList";
    private static final String REDIRECT_HINT_LIST = "redirect:/game/progress/viewHints/";

    private final TeamService teamService;
    private final CypherService cypherService;
    private final ProgressService progressService;
    private final StatusService statusService;
    private final HintService hintService;
    private final HintTakenService hintTakenService;
    private final GameLogicService gameLogicService;

    @Autowired
    public ProgressController(final TeamService teamService,
                              final CypherService cypherService,
                              final ProgressService progressService,
                              final HintService hintService,
                              final HintTakenService hintTakenService,
                              final StatusService statusService,
                              final GameLogicService gameLogicService) {
        this.teamService = teamService;
        this.cypherService = cypherService;
        this.progressService = progressService;
        this.hintService = hintService;
        this.hintTakenService = hintTakenService;
        this.statusService = statusService;
        this.gameLogicService = gameLogicService;
    }

    @GetMapping
    public String listProgress(final Model model) {
        model.addAttribute("cyphers", cypherService.getAllCyphersOrderByStageAsc());
        return PROGRESS_LIST;
    }

    @GetMapping(value = "/teamsList")
    public String listTeams(final @RequestParam(value = "search", required = false) String searchString,
                            final Model model) {
        List<Team> allTeams = teamService.getAll();
        model.addAttribute("allTeamsStarted", progressService.isGameStartedForAllTeams());
        model.addAttribute("search", searchString);
        model.addAttribute("teams", progressService.getSearchedTeams(searchString));
        if (progressService.getTeamsWithPendingCypher(allTeams).size() > 0) {
            model.addAttribute("minMaxStages", progressService.getCurrentStageRangeOfAllTeams());
        }
        model.addAttribute("teamsFinished", progressService.getNumberOfFinishedTeams());
        List<TeamProgressWithTeam> teamsProgres = progressService.getSearchedTeamsWithTeamProgress(searchString);
        model.addAttribute("teamsProgress", teamsProgres);
        return TEAM_LIST;
    }

    @GetMapping(value = "/startGame")
    public String initializeGameForAllTeams() {
        gameLogicService.initializeGameForAllTeams();
        return REDIRECT_TEAM_LIST;
    }

    @GetMapping(value = "/teamDetail")
    public String teamProgress(final @RequestParam("teamId") Long teamId, final Model model) {
        Team team = teamService.getTeam(teamId);
        model.addAttribute("team", team);
        model.addAttribute("cyphers", cypherService.getAllCyphersOrderByStageAsc());
        model.addAttribute("cyphersStatuses", progressService.getCyphersStatuses(team));
        return TEAM_DETAIL;
    }

    @GetMapping(value = "/stage")
    public String stageProgress(final @RequestParam("cypherId") Long cypherId,
                                final @RequestParam(value = "search", required = false) String searchString,
                                final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        model.addAttribute("cypher", cypher);
        model.addAttribute("teamsStatuses", progressService.getTeamsStatuses(cypher));
        model.addAttribute("search", searchString);
        model.addAttribute("teams", progressService.getSearchedTeams(searchString));
        return PROGRESS_STAGE;
    }

    @GetMapping(value = "/changeStatus/{cypherId}")
    public String changeCypherStatus(final @RequestParam(value = "search", required = false) String searchString,
                                     final @PathVariable("cypherId") Long cypherId,
                                      final @RequestParam("teamId") Long teamId,
                                      final @RequestParam("newStatus") String newStatus,
                                      final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        Team team = teamService.getTeam(teamId);
        CypherStatus cypherStatus = CypherStatusFactory.create(newStatus);
        statusService.markCypher(cypher, team, cypherStatus);

        model.addAttribute("cypher", cypher);
        model.addAttribute("teams", progressService.getSearchedTeams(searchString));
        model.addAttribute("teamsStatuses", progressService.getTeamsStatuses(cypher));
        model.addAttribute("search", searchString);
        return PROGRESS_STAGE;
    }

    @GetMapping(value = "/viewHints/{cypherId}")
    public String viewHintsForCypher(final @PathVariable("cypherId") Long cypherId,
                                      final @RequestParam("teamId") Long teamId,
                                      final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        Team team = teamService.getTeam(teamId);
        model.addAttribute("cypher", cypher);
        model.addAttribute("team", team);
        model.addAttribute("hintsTaken", hintTakenService.getAllByTeamAndCypher(team, cypher));
        model.addAttribute("hintsNotTaken", hintService.getAllNotTakenByTeamAndCypher(team, cypher));
        return HINT_LIST;
    }

    @PostMapping("/takeHintOfCypher")
    public ResponseEntity viewHintsForCypherByTeam(final @RequestParam("teamId") Long teamId,
                                                 final @RequestParam("hintId") Long hintId,
                                                 final @RequestParam("pin") String pin) {
        Team team = teamService.getTeam(teamId);
        if (pin.equals(team.getPin())) {
            Hint hint = hintService.getHint(hintId);
            hintTakenService.takeHint(team, hint);
            return ResponseEntity
                    .ok()
                    .header("content-type", "application/json")
                    .body("{}");

        } else {
            return ResponseEntity
                    .badRequest()
                    .header("content-type", "application/json")
                    .body("{}");
        }
    }

    @GetMapping("/revertHint")
    public String revertHint(final @RequestParam("teamId") Long teamId,
                                             final @RequestParam("hintId") Long hintId,
                                             final @RequestParam("cypherId") Long cypherId) {
        Team team = teamService.getTeam(teamId);
        Hint hint = hintService.getHint(hintId);
        hintTakenService.revertHint(team, hint);
        return REDIRECT_HINT_LIST
                .concat(cypherId.toString())
                .concat("?teamId=")
                .concat(teamId.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleError() {
        return ERROR;
    }
}
