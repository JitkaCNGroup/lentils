package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.dto.CodewordFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.CypherGameInfoService;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.GameLogicService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.HintTakenService;
import dk.cngroup.lentils.service.ScoreService;
import dk.cngroup.lentils.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class ClientController {

    private static final String VIEW_CLIENT_CYPHER_LIST = "client/cypher/list";
    private static final String VIEW_CLIENT_CYPHER_DETAIL = "client/cypher/detail";
    private static final String VIEW_CLIENT_HINT_LIST = "client/hint/list";
    private static final String VIEW_CLIENT_CYPHER_TRAP = "client/cypher/trap";
    private static final String REDIRECT_CLIENT_CYPHER = "redirect:/cypher/";
    private static final String REDIRECT_CLIENT_CYPHER_TRAP = "redirect:/cypher/trap/";
    private static final String FORM_OBJECT_NAME = "codeword";
    private static final String GUESS_FIELD_NAME = "guess";

    private static final String TEMPLATE_ATTR_CYPHER = "cypher";
    private static final String TEMPLATE_ATTR_CYPHER_GAME_INFOS = "cypherGameInfos";
    private static final String TEMPLATE_ATTR_FINAL_VIEW_ALLOWED = "finalViewAllowed";
    private static final String TEMPLATE_ATTR_GAME_STARTED = "gameStarted";
    private static final String TEMPLATE_ATTR_HINTS_NOT_TAKEN = "hintsNotTaken";
    private static final String TEMPLATE_ATTR_HINTS_TAKEN = "hintsTaken";
    private static final String TEMPLATE_ATTR_NEXT_CYPHER = "nextCypher";
    private static final String TEMPLATE_ATTR_RESULTS_TIME = "resultsTime";
    private static final String TEMPLATE_ATTR_SCORE = "score";
    private static final String TEMPLATE_ATTR_STATUS = "status";
    private static final String TEMPLATE_ATTR_TEAM = "team";

    private final CypherService cypherService;
    private final HintService hintService;
    private final HintTakenService hintTakenService;
    private final StatusService statusService;
    private final CypherGameInfoService cypherGameInfoService;
    private final ScoreService scoreService;
    private final GameLogicService gameLogicService;
    private final MessageSource messageSource;

    @Autowired
    @SuppressWarnings("checkstyle:parameternumber")
    public ClientController(final CypherService cypherService,
                            final StatusService statusService,
                            final HintService hintService,
                            final HintTakenService hintTakenService,
                            final CypherGameInfoService cypherGameInfoService,
                            final ScoreService scoreService,
                            final GameLogicService gameLogicService,
                            final MessageSource messageSource) {
        this.cypherService = cypherService;
        this.hintService = hintService;
        this.hintTakenService = hintTakenService;
        this.statusService = statusService;
        this.cypherGameInfoService = cypherGameInfoService;
        this.scoreService = scoreService;
        this.gameLogicService = gameLogicService;
        this.messageSource = messageSource;
    }

    @GetMapping(value = "cypher")
    public String clientWelcomePage(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        List<Cypher> cyphers = cypherService.getAllCyphersOrderByStageAsc();
        if (!cyphers.isEmpty() && statusService.isStatusInDbByCypherAndTeam(cyphers.get(0), user.getTeam())) {
            model.addAttribute(TEMPLATE_ATTR_GAME_STARTED, true);
            model.addAttribute(TEMPLATE_ATTR_SCORE, scoreService.getScoreByTeam(user.getTeam()));
            model.addAttribute(TEMPLATE_ATTR_CYPHER_GAME_INFOS,
                    cypherGameInfoService.getAllByTeamIdAndStatusIsNotLocked(user.getTeam().getTeamId()));
            checkTeamAllowedToViewFinalPlace(user, model);
        } else {
            model.addAttribute(TEMPLATE_ATTR_GAME_STARTED, false);
            model.addAttribute(TEMPLATE_ATTR_FINAL_VIEW_ALLOWED, false);
        }
        model.addAttribute(TEMPLATE_ATTR_TEAM, user.getTeam());
        return VIEW_CLIENT_CYPHER_LIST;
    }

    @GetMapping(value = "cypher/startGame")
    public String initializeGame(@AuthenticationPrincipal final CustomUserDetails user) {
        gameLogicService.initializeGameForTeam(user.getTeam());
        return REDIRECT_CLIENT_CYPHER;
    }

    @GetMapping(value = "cypher/{id}")
    public String cypherDetail(@PathVariable("id") final Long id,
                               @AuthenticationPrincipal final CustomUserDetails user,
                               final Model model) {
        Cypher cypher = cypherService.getCypher(id);
        CypherStatus status = statusService.getCypherStatusByTeamAndCypher(user.getTeam(), cypher);
        if (status.equals(CypherStatus.LOCKED)) {
            throw new ResourceNotFoundException("locked cypher", id);
        }
        CodewordFormDTO codewordFormDto = new CodewordFormDTO();
        setDetailModelAttributes(model, user, cypher, status, codewordFormDto);

        return VIEW_CLIENT_CYPHER_DETAIL;
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

    @PostMapping(value = "cypher/verify/{id}")
    public String verifyCodeword(@PathVariable("id") final Long id,
                                 @Valid @ModelAttribute(FORM_OBJECT_NAME) final CodewordFormDTO codewordFormDto,
                                 @AuthenticationPrincipal final CustomUserDetails user,
                                 final BindingResult result,
                                 final Model model) {
        Cypher cypher = cypherService.getCypher(id);
        CypherStatus status = statusService.getCypherStatusByTeamAndCypher(user.getTeam(), cypher);

        if (!gameLogicService.isGameInProgress()) {
            FieldError error = new FieldError(FORM_OBJECT_NAME,
                    GUESS_FIELD_NAME,
                    messageSource.getMessage("label.error.gameended", null, LocaleContextHolder.getLocale()));
            result.addError(error);
            setDetailModelAttributes(model, user, cypher, status, codewordFormDto);
            return VIEW_CLIENT_CYPHER_DETAIL;
        }

        if (status != CypherStatus.PENDING) {
            setDetailModelAttributes(model, user, cypher, status, codewordFormDto);
            return VIEW_CLIENT_CYPHER_DETAIL;
        }

        if (cypherService.checkCodeword(cypher, codewordFormDto.getGuess())) {
            statusService.markCypher(cypher, user.getTeam(), CypherStatus.SOLVED);
            return REDIRECT_CLIENT_CYPHER + cypher.getCypherId();
        }

        if (cypherService.checkTrapCodeword(cypher, codewordFormDto.getGuess())) {
            return REDIRECT_CLIENT_CYPHER_TRAP + cypher.getCypherId();
        }

        FieldError error = new FieldError(
                FORM_OBJECT_NAME,
                GUESS_FIELD_NAME,
                messageSource.getMessage("label.error.badsolution", null, LocaleContextHolder.getLocale()));
        result.addError(error);
        setDetailModelAttributes(model, user, cypher, status, codewordFormDto);

        return VIEW_CLIENT_CYPHER_DETAIL;
    }

    @GetMapping("cypher/trap/{id}")
    public String trapScreen(@PathVariable("id") final Long id,
                             @AuthenticationPrincipal final CustomUserDetails user,
                             final Model model) {
        model.addAttribute(TEMPLATE_ATTR_TEAM, user.getTeam());
        model.addAttribute(TEMPLATE_ATTR_SCORE, scoreService.getScoreByTeam(user.getTeam()));
        model.addAttribute(TEMPLATE_ATTR_CYPHER, cypherService.getCypher(id));
        return VIEW_CLIENT_CYPHER_TRAP;
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

    @PostMapping(value = "cypher/giveUp")
    public String skipCypher(final Cypher cypher, @AuthenticationPrincipal final CustomUserDetails user) {
        if (!gameLogicService.isGameInProgress()) {
            return REDIRECT_CLIENT_CYPHER + cypher.getCypherId() + "?gameEnded=true";
        }

        CypherStatus cypherStatus = statusService.getCypherStatusByTeamAndCypher(user.getTeam(), cypher);
        if (cypherStatus != (CypherStatus.PENDING)) {
            return REDIRECT_CLIENT_CYPHER + cypher.getCypherId() + "?wrongStatus=true";
        }

        statusService.markCypher(cypher, user.getTeam(), CypherStatus.SKIPPED);
        return REDIRECT_CLIENT_CYPHER + cypher.getCypherId();
    }

    private void setDetailModelAttributes(
            final Model model,
            final CustomUserDetails user,
            final Cypher cypher,
            final CypherStatus status,
            final CodewordFormDTO codewordFormDto) {
        model.addAttribute(TEMPLATE_ATTR_TEAM, user.getTeam());
        model.addAttribute(TEMPLATE_ATTR_CYPHER, cypher);
        model.addAttribute(TEMPLATE_ATTR_STATUS, status.name());
        model.addAttribute(TEMPLATE_ATTR_HINTS_TAKEN, hintTakenService.getAllByTeamAndCypher(user.getTeam(), cypher));
        model.addAttribute(TEMPLATE_ATTR_NEXT_CYPHER, cypherService.getNext(cypher.getStage()));
        model.addAttribute(FORM_OBJECT_NAME, codewordFormDto);
        model.addAttribute(TEMPLATE_ATTR_SCORE, scoreService.getScoreByTeam(user.getTeam()));
        model.addAttribute(TEMPLATE_ATTR_FINAL_VIEW_ALLOWED,
                gameLogicService.allowPlayersToViewFinalPlace(user.getTeam()));
    }

    private void checkTeamAllowedToViewFinalPlace(@AuthenticationPrincipal final CustomUserDetails user,
                                                  final Model model) {
        if (gameLogicService.allowPlayersToViewFinalPlace(user.getTeam())) {
            model.addAttribute(TEMPLATE_ATTR_FINAL_VIEW_ALLOWED, true);
            model.addAttribute(TEMPLATE_ATTR_RESULTS_TIME, gameLogicService.getFinalPlaceResultsTime());
        }
    }
}
