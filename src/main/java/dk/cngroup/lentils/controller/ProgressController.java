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
    private static final String VIEW_ORGANIZER_PROGRESS_STAGE = "progress/stage";
    private static final String VIEW_ORGANIZER_PROGRESS_LIST = "progress/list";
    private static final String VIEW_ORGANIZER_PROGRESS_TEAMLIST = "progress/teamList";
    private static final String VIEW_ORGANIZER_PROGRESS_TEAMDETAIL = "progress/teamDetail";
    private static final String VIEW_ORGANIZER_PROGRESS_GETHINTLIST = "progress/getHintList";
    private static final String REDIRECT_ORGANIZER_GAME_PROGRESS_TEAMSLIST = "redirect:/game/progress/teamsList";
    private static final String REDIRECT_ORGANIZER_GAME_PROGRESS_VIEWHINTS = "redirect:/game/progress/viewHints/";
    private static final String ERROR = "error/error";

    private static final String TEMPLATE_ATTR_CYPHER = "cypher";
    private static final String TEMPLATE_ATTR_CYPHERS = "cyphers";
    private static final String TEMPLATE_ATTR_CYPHERS_STATUSES = "cyphersStatuses";
    private static final String TEMPLATE_ATTR_SEARCH = "search";
    private static final String TEMPLATE_ATTR_TEAM = "team";
    private static final String TEMPLATE_ATTR_TEAMS = "teams";
    private static final String TEMPLATE_ATTR_TEAMS_PROGRESS = "teamsProgress";
    private static final String TEMPLATE_ATTR_TEAMS_STATUSES = "teamsStatuses";
    private static final String TEMPLATE_ATTR_TEAMS_FINISHED = "teamsFinished";
    private static final String TEMPLATE_ATTR_PENDING_TEAMS = "pendingTeams";
    private static final String TEMPLATE_ATTR_OTHER_TEAMS = "otherTeams";
    private static final String TEMPLATE_ATTR_ALL_TEAMS_STARTED = "allTeamsStarted";
    private static final String TEMPLATE_ATTR_VIEW_OTHER_TEAMS = "viewOtherTeams";
    private static final String TEMPLATE_ATTR_MIN_MAX_STAGES = "minMaxStages";
    private static final String TEMPLATE_ATTR_VIEW_HINTS_TAKEN = "hintsTaken";
    private static final String TEMPLATE_ATTR_VIEW_HINTS_NOT_TAKEN = "hintsNotTaken";

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
        model.addAttribute(TEMPLATE_ATTR_CYPHERS, cypherService.getAllCyphersOrderByStageAsc());
        return VIEW_ORGANIZER_PROGRESS_LIST;
    }

    @GetMapping(value = "/teamsList")
    public String listTeams(final @RequestParam(value = "search", required = false) String searchString,
                            final Model model) {
        List<Team> allTeams = teamService.getAll();
        model.addAttribute(TEMPLATE_ATTR_ALL_TEAMS_STARTED, progressService.isGameStartedForAllTeams());
        model.addAttribute(TEMPLATE_ATTR_SEARCH, searchString);
        if (!progressService.getTeamsWithPendingCypher(allTeams).isEmpty()) {
            model.addAttribute(TEMPLATE_ATTR_MIN_MAX_STAGES, progressService.getCurrentStageRangeOfAllTeams());
        }
        model.addAttribute(TEMPLATE_ATTR_TEAMS_FINISHED, progressService.getNumberOfFinishedTeams());
        List<TeamProgressWithTeam> teamsProgres = progressService.getSearchedTeamsWithTeamProgress(searchString);
        model.addAttribute(TEMPLATE_ATTR_TEAMS_PROGRESS, teamsProgres);
        return VIEW_ORGANIZER_PROGRESS_TEAMLIST;
    }

    @GetMapping(value = "/startGame")
    public String initializeGameForAllTeams() {
        gameLogicService.initializeGameForAllTeams();
        return REDIRECT_ORGANIZER_GAME_PROGRESS_TEAMSLIST;
    }

    @GetMapping(value = "/teamDetail")
    public String teamProgress(final @RequestParam("teamId") Long teamId, final Model model) {
        Team team = teamService.getTeam(teamId);
        model.addAttribute(TEMPLATE_ATTR_TEAM, team);
        model.addAttribute(TEMPLATE_ATTR_CYPHERS, cypherService.getAllCyphersOrderByStageAsc());
        model.addAttribute(TEMPLATE_ATTR_CYPHERS_STATUSES, progressService.getCyphersStatuses(team));
        return VIEW_ORGANIZER_PROGRESS_TEAMDETAIL;
    }

    @GetMapping(value = "/stage")
    public String stageProgress(final @RequestParam("cypherId") Long cypherId,
                                final @RequestParam(value = "search", required = false) String searchString,
                                final @RequestParam(
                                        value = "viewOtherTeams",
                                        required = false,
                                        defaultValue = "false") Boolean viewOtherTeams,
                                final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);

        model.addAttribute(TEMPLATE_ATTR_CYPHER, cypher);
        model.addAttribute(TEMPLATE_ATTR_TEAMS_STATUSES, progressService.getTeamsStatuses(cypher));
        model.addAttribute(TEMPLATE_ATTR_SEARCH, searchString);
        model.addAttribute(TEMPLATE_ATTR_PENDING_TEAMS,
                progressService.getSearchedTeamsWithSpecificStatusAtSpecificCypher(
                        searchString,
                        cypher,
                        CypherStatus.PENDING,
                        true));
        if (viewOtherTeams) {
            model.addAttribute(TEMPLATE_ATTR_OTHER_TEAMS,
                    progressService.getSearchedTeamsWithSpecificStatusAtSpecificCypher(
                            searchString,
                            cypher,
                            CypherStatus.PENDING,
                            false));
            model.addAttribute(TEMPLATE_ATTR_VIEW_OTHER_TEAMS, true);
        }
        return VIEW_ORGANIZER_PROGRESS_STAGE;
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

        model.addAttribute(TEMPLATE_ATTR_CYPHER, cypher);
        model.addAttribute(TEMPLATE_ATTR_TEAMS, progressService.getSearchedTeams(searchString));
        model.addAttribute(TEMPLATE_ATTR_TEAMS_STATUSES, progressService.getTeamsStatuses(cypher));
        model.addAttribute(TEMPLATE_ATTR_SEARCH, searchString);

        return "redirect:/game/progress/stage?cypherId=" + cypherId + "&search=" + searchString;
    }

    @GetMapping(value = "/viewHints/{cypherId}")
    public String viewHintsForCypher(final @PathVariable("cypherId") Long cypherId,
                                      final @RequestParam("teamId") Long teamId,
                                      final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        Team team = teamService.getTeam(teamId);
        model.addAttribute(TEMPLATE_ATTR_CYPHER, cypher);
        model.addAttribute(TEMPLATE_ATTR_TEAM, team);
        model.addAttribute(TEMPLATE_ATTR_VIEW_HINTS_TAKEN, hintTakenService.getAllByTeamAndCypher(team, cypher));
        model.addAttribute(TEMPLATE_ATTR_VIEW_HINTS_NOT_TAKEN, hintService.getAllNotTakenByTeamAndCypher(team, cypher));
        return VIEW_ORGANIZER_PROGRESS_GETHINTLIST;
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
        return REDIRECT_ORGANIZER_GAME_PROGRESS_VIEWHINTS
                .concat(cypherId.toString())
                .concat("?teamId=")
                .concat(teamId.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleError() {
        return ERROR;
    }
}
