package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.service.FinalPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/finalplace")
public class FinalPlaceController {
    private static final String VIEW_FINALPLACE_FORM = "finalplace/form";
    private static final String REDIRECT_FINALPLACE_FORM = "redirect:/finalplace/";

    private FinalPlaceService finalPlaceService;

    @Autowired
    public FinalPlaceController(FinalPlaceService finalPlaceService) {
        this.finalPlaceService = finalPlaceService;
    }

    @GetMapping(value = "/")
    public String finalPlace(Model model) {
        model.addAttribute("finalplace", finalPlaceService.getFinalPlace());
        return VIEW_FINALPLACE_FORM;
    }

    @PostMapping(value = "/save")
    public String saveFinalPlace(@Valid FinalPlace finalPlace, Model model) {
        finalPlaceService.save(finalPlace);
        return REDIRECT_FINALPLACE_FORM;
    }
}
