package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.exception.NotYetTimeToShowOpeningTimeException;
import dk.cngroup.lentils.service.FinalPlaceService;
import dk.cngroup.lentils.service.GameLogicService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ClientFinalPlaceController(final FinalPlaceService finalPlaceService,
                                      final GameLogicService gameLogicService) {
        this.finalPlaceService = finalPlaceService;
        this.gameLogicService = gameLogicService;
    }

    @GetMapping(value = "finalplace")
    public String finalPlaceDetail(final Model model) {
        if (!gameLogicService.allowPlayersToViewFinalPlace()) {
            throw new NotYetTimeToShowOpeningTimeException();
        }
        model.addAttribute("finalplace", finalPlaceService.getFinalPlace());
        return CLIENT_VIEW_FINAL_PLACE_DETAIL;
    }
}
