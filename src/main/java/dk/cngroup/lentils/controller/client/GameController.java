package dk.cngroup.lentils.controller.client;

import dk.cngroup.lentils.dto.CodewordFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.GameLogicService;
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

@Controller
@RequestMapping("/")
public class GameController {

    private static final String VIEW_CLIENT_CYPHER_DETAIL = "client/cypher/detail";
    private static final String REDIRECT_CLIENT_CYPHER = "redirect:/cypher/";
    private static final String REDIRECT_CLIENT_CYPHER_TRAP = "redirect:/cypher/trap/";
    private static final String FORM_OBJECT_NAME = "codeword";
    private static final String GUESS_FIELD_NAME = "guess";

    private static final String TEMPLATE_ATTR_CYPHER = "cypher";
    private static final String TEMPLATE_ATTR_FINAL_VIEW_ALLOWED = "finalViewAllowed";
    private static final String TEMPLATE_ATTR_HINTS_TAKEN = "hintsTaken";
    private static final String TEMPLATE_ATTR_NEXT_CYPHER = "nextCypher";
    private static final String TEMPLATE_ATTR_SCORE = "score";
    private static final String TEMPLATE_ATTR_STATUS = "status";
    private static final String TEMPLATE_ATTR_TEAM = "team";

    private final CypherService cypherService;
    private final HintTakenService hintTakenService;
    private final StatusService statusService;
    private final ScoreService scoreService;
    private final GameLogicService gameLogicService;
    private final MessageSource messageSource;

    @Autowired
    public GameController(final CypherService cypherService,
                            final StatusService statusService,
                            final HintTakenService hintTakenService,
                            final ScoreService scoreService,
                            final GameLogicService gameLogicService,
                            final MessageSource messageSource) {
        this.cypherService = cypherService;
        this.hintTakenService = hintTakenService;
        this.statusService = statusService;
        this.scoreService = scoreService;
        this.gameLogicService = gameLogicService;
        this.messageSource = messageSource;
    }

    @GetMapping(value = "cypher/startGame")
    public String initializeGame(@AuthenticationPrincipal final CustomUserDetails user) {
        gameLogicService.initializeGameForTeam(user.getTeam());
        return REDIRECT_CLIENT_CYPHER;
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
}
