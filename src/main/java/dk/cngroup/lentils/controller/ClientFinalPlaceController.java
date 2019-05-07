package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.exception.FinalPlaceNotYetAccessibleException;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.FinalPlaceService;
import dk.cngroup.lentils.service.GameLogicService;
import dk.cngroup.lentils.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ClientFinalPlaceController {

    private static final String CLIENT_VIEW_FINAL_PLACE_DETAIL = "client/finalplace/detail";

    private final FinalPlaceService finalPlaceService;
    private final GameLogicService gameLogicService;
    private final ScoreService scoreService;

    @Autowired
    public ClientFinalPlaceController(final FinalPlaceService finalPlaceService,
                                      final GameLogicService gameLogicService,
                                      final ScoreService scoreService) {
        this.finalPlaceService = finalPlaceService;
        this.gameLogicService = gameLogicService;
        this.scoreService = scoreService;
    }

    @GetMapping(value = "finalplace")
    public String finalPlaceDetail(@AuthenticationPrincipal final CustomUserDetails user, final Model model) {
        if (!gameLogicService.allowPlayersToViewFinalPlace(user.getTeam())) {
            throw new FinalPlaceNotYetAccessibleException();
        }
        model.addAttribute("finalplace", finalPlaceService.getFinalPlace());
        model.addAttribute("score", scoreService.getScoreByTeam(user.getTeam()));
        model.addAttribute("team", user.getTeam());
        return CLIENT_VIEW_FINAL_PLACE_DETAIL;
    }
}
