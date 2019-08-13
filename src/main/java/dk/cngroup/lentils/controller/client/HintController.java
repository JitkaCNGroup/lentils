package dk.cngroup.lentils.controller.client;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.GameLogicService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.HintTakenService;
import dk.cngroup.lentils.service.ScoreService;
import dk.cngroup.lentils.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("clientHintController")
@RequestMapping("/")
public class HintController {

    private static final String VIEW_CLIENT_HINT_LIST = "client/hint/list";
    private static final String REDIRECT_CLIENT_CYPHER = "redirect:/cypher/";

    private static final String TEMPLATE_ATTR_CYPHER = "cypher";
    private static final String TEMPLATE_ATTR_HINTS_NOT_TAKEN = "hintsNotTaken";
    private static final String TEMPLATE_ATTR_HINTS_TAKEN = "hintsTaken";
    private static final String TEMPLATE_ATTR_SCORE = "score";
    private static final String TEMPLATE_ATTR_TEAM = "team";

    private final CypherService cypherService;
    private final HintService hintService;
    private final HintTakenService hintTakenService;
    private final StatusService statusService;
    private final ScoreService scoreService;
    private final GameLogicService gameLogicService;

    @Autowired
    public HintController(final CypherService cypherService,
                            final StatusService statusService,
                            final HintService hintService,
                            final HintTakenService hintTakenService,
                            final ScoreService scoreService,
                            final GameLogicService gameLogicService) {
        this.cypherService = cypherService;
        this.hintService = hintService;
        this.hintTakenService = hintTakenService;
        this.statusService = statusService;
        this.scoreService = scoreService;
        this.gameLogicService = gameLogicService;
    }

    @GetMapping(value = "cypher/{id}/hint")
    public String hintDetail(@PathVariable("id") final Long id,
                             @AuthenticationPrincipal final CustomUserDetails user,
                             final Model model) {
        Cypher cypher = cypherService.getCypher(id);
        CypherStatus status = statusService.getCypherStatusByTeamAndCypher(user.getTeam(), cypher);
        if (status.equals(CypherStatus.LOCKED)) {
            throw new ResourceNotFoundException("locked cypher", id);
        }

        model.addAttribute(TEMPLATE_ATTR_TEAM, user.getTeam());
        model.addAttribute(TEMPLATE_ATTR_CYPHER, cypher);
        model.addAttribute(TEMPLATE_ATTR_HINTS_TAKEN, hintTakenService.getAllByTeamAndCypher(user.getTeam(), cypher));
        model.addAttribute(TEMPLATE_ATTR_HINTS_NOT_TAKEN,
                hintService.getAllNotTakenByTeamAndCypher(user.getTeam(), cypher));
        model.addAttribute(TEMPLATE_ATTR_SCORE, scoreService.getScoreByTeam(user.getTeam()));
        return VIEW_CLIENT_HINT_LIST;
    }

    @PostMapping(value = "cypher/takeHint/{hintId}")
    public String getHint(@PathVariable("hintId") final Long id,
                          @AuthenticationPrincipal final CustomUserDetails user,
                          final Cypher cypher) {
        if (!gameLogicService.isGameInProgress()) {
            return REDIRECT_CLIENT_CYPHER + cypher.getCypherId() + "/hint?gameEnded=true";
        }

        CypherStatus cypherStatus = statusService.getCypherStatusByTeamAndCypher(user.getTeam(), cypher);
        if (cypherStatus != (CypherStatus.PENDING)) {
            return REDIRECT_CLIENT_CYPHER + cypher.getCypherId() + "/hint?wrongStatus=true";
        }

        Team team = user.getTeam();
        Hint hint = hintService.getHint(id);
        hintTakenService.takeHint(team, hint);
        return REDIRECT_CLIENT_CYPHER + cypher.getCypherId() + "/hint";
    }
}
