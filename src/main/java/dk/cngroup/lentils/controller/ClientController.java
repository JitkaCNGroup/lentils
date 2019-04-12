package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.formEntity.Codeword;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.CypherGameInfoService;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.FinalPlaceService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.HintTakenService;
import dk.cngroup.lentils.service.ScoreService;
import dk.cngroup.lentils.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/")
public class ClientController {

    private static final String CLIENT_VIEW_CYPHER_LIST = "client/cypher/list";
    private static final String CLIENT_VIEW_CYPHER_DETAIL = "client/cypher/detail";
    private static final String CLIENT_VIEW_HINT_LIST = "client/hint/list";
    private static final String CLIENT_TRAP_SCREEN = "client/cypher/trap";
    private static final String REDIRECT_TO_CLIENT_CYPHER_DETAIL = "redirect:/cypher/";
    private static final String REDIRECT_TO_TRAP_SCREEN = "redirect:/cypher/lets-play-a-game";

    private final CypherService cypherService;
    private final HintService hintService;
    private final HintTakenService hintTakenService;
    private final StatusService statusService;
    private final CypherGameInfoService cypherGameInfoService;
    private final ScoreService scoreService;
    private final FinalPlaceService finalPlaceService;

    @Autowired
    public ClientController(final CypherService cypherService,
                            final StatusService statusService,
                            final HintService hintService,
                            final HintTakenService hintTakenService,
                            final CypherGameInfoService cypherGameInfoService,
                            final ScoreService scoreService,
                            final FinalPlaceService finalPlaceService) {
        this.cypherService = cypherService;
        this.hintService = hintService;
        this.hintTakenService = hintTakenService;
        this.statusService = statusService;
        this.cypherGameInfoService = cypherGameInfoService;
        this.scoreService = scoreService;
        this.finalPlaceService = finalPlaceService;
    }

    @GetMapping(value = "cypher")
    public String listAllCyphers(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        model.addAttribute("cypherGameInfos", cypherGameInfoService.getAllByTeamId(user.getTeam().getTeamId()));
        model.addAttribute("team", user.getTeam());
        model.addAttribute("score", scoreService.getScoreByTeam(user.getTeam()));
        return CLIENT_VIEW_CYPHER_LIST;
    }

    @GetMapping(value = "cypher/{id}")
    public String cypherDetail(@PathVariable("id") final Long id,
                               @AuthenticationPrincipal final CustomUserDetails user,
                               final Model model) {
        Cypher cypher = cypherService.getCypher(id);
        String status = statusService.getStatusNameByTeamAndCypher(user.getTeam(), cypher);

        Codeword codeword = new Codeword();
        setDetailModeAttributes(model, user, cypher, status, codeword);

        return CLIENT_VIEW_CYPHER_DETAIL;
    }

    @GetMapping(value = "hint")
    public String hintDetail(@AuthenticationPrincipal final CustomUserDetails user,
                            final Cypher cypher,
                            final Model model) {
        model.addAttribute("team", user.getTeam());
        model.addAttribute("cypher", cypher);
        model.addAttribute("hintsTaken", hintTakenService.getAllByTeamAndCypher(user.getTeam(), cypher));
        model.addAttribute("hintsNotTaken", hintService.getAllNotTakenByTeamAndCypher(user.getTeam(), cypher));
        return CLIENT_VIEW_HINT_LIST;
    }

    @PostMapping(value = "cypher/verify/{id}")
    public String verifyCodeword(@PathVariable("id") final Long id,
                                 @Valid final Codeword codeword,
                                 @AuthenticationPrincipal final CustomUserDetails user,
                                 final BindingResult result,
                                 final Model model) {
        Cypher cypher = cypherService.getCypher(id);
        String status = statusService.getStatusNameByTeamAndCypher(user.getTeam(), cypher);

        final FinalPlace finalPlace = finalPlaceService.getFinalPlace();
        if (finalPlace.getOpeningTime() == null || finalPlace.getOpeningTime().isBefore(LocalDateTime.now())) {
            FieldError error = new FieldError("codeword", "guess", "Hra již byla ukončena");
            result.addError(error);

            setDetailModeAttributes(model, user, cypher, status, codeword);
            return CLIENT_VIEW_CYPHER_DETAIL;
        }

        if (cypherService.checkCodeword(cypher, codeword.getGuess())) {
            statusService.markCypher(cypherService.getCypher(cypher.getCypherId()),
                    user.getTeam(),
                    CypherStatus.SOLVED);
            return REDIRECT_TO_CLIENT_CYPHER_DETAIL + cypher.getCypherId();
        }

        if (cypherService.checkTrapCodeword(cypher, codeword.getGuess())) {
            return REDIRECT_TO_TRAP_SCREEN;
        }

        FieldError error = new FieldError("codeword", "guess", "Špatné řešení, zkuste se víc zamyslet :-)");
        result.addError(error);
        setDetailModeAttributes(model, user, cypher, status, codeword);

        return CLIENT_VIEW_CYPHER_DETAIL;
    }

    @GetMapping("cypher/lets-play-a-game")
    public String trapScreen() {
        return CLIENT_TRAP_SCREEN;
    }

    @PostMapping(value = "cypher/takeHint/{hintId}")
    public String getHint(@PathVariable("hintId") final Long id,
                          @AuthenticationPrincipal final CustomUserDetails user,
                          final Cypher cypher) {
        Team team = user.getTeam();
        Hint hint = hintService.getHint(id);
        hintTakenService.takeHint(team, hint);
        return REDIRECT_TO_CLIENT_CYPHER_DETAIL + cypher.getCypherId();
    }

    @PostMapping(value = "cypher/giveUp")
    public String skipCypher(final Cypher cypher, @AuthenticationPrincipal final CustomUserDetails user) {
        CypherStatus cypherStatus = statusService.getCypherStatusByTeamAndCypher(user.getTeam(), cypher);
        if (cypherStatus.equals(CypherStatus.PENDING)) {
            statusService.markCypher(cypher, user.getTeam(), CypherStatus.SKIPPED);
        }
        if (cypherService.getNext(cypher.getStage()) != null) {
            return REDIRECT_TO_CLIENT_CYPHER_DETAIL + cypherService.getNext(cypher.getStage()).getCypherId();
        }
        return REDIRECT_TO_CLIENT_CYPHER_DETAIL + cypher.getCypherId();
    }

    private void setDetailModeAttributes(
            final Model model,
            final CustomUserDetails user,
            final Cypher cypher,
            final String status,
            final Codeword codeword) {
        model.addAttribute("team", user.getTeam());
        model.addAttribute("cypher", cypher);
        model.addAttribute("status", status);
        model.addAttribute("hintsTaken", hintTakenService.getAllByTeamAndCypher(user.getTeam(), cypher));
        model.addAttribute("nextCypher", cypherService.getNext(cypher.getStage()));
        model.addAttribute("codeword", codeword);
    }
}
