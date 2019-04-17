package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.service.FinalPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;

@Controller
@RequestMapping("/admin/finalplace")
public class FinalPlaceController {
    private static final String VIEW_FINALPLACE_FORM = "finalplace/form";
    private static final String REDIRECT_FINALPLACE_FORM = "redirect:/admin/finalplace/";

    private FinalPlaceService finalPlaceService;

    @Autowired
    public FinalPlaceController(final FinalPlaceService finalPlaceService) {
        this.finalPlaceService = finalPlaceService;
    }

    @GetMapping(value = "/")
    public String finalPlace(final Model model) {
        model.addAttribute("finalplace", finalPlaceService.getFinalPlace());
        return VIEW_FINALPLACE_FORM;
    }

    @PostMapping(value = "/update")
    public String saveFinalPlace(@Valid final FinalPlace finalPlace, final Model model) {
        finalPlaceService.save(finalPlace);
        return REDIRECT_FINALPLACE_FORM;
    }
}
