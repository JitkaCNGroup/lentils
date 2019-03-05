package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/hint")
public class HintController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HintController.class);

    private final String VIEW_HINT_LIST = "hint/list";
    private final String VIEW_HINT = "hint/detail";
    private final String REDIRECT_HINT_LIST = "redirect:/hint/list";

    private CypherService cypherService;
    private HintService hintService;

    @Autowired
    public HintController(CypherService cypherService, HintService hintService) {
        this.cypherService = cypherService;
        this.hintService = hintService;
    }

    @GetMapping(value = "/list")
    public String hints(@RequestParam("cypherId")Long cypherId, Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        model.addAttribute("cypher", cypher);
        return VIEW_HINT_LIST;
    }

    @GetMapping(value = "/delete/{hintId}")
    public String deleteHint(@PathVariable Long hintId) {
        Hint hint = hintService.getHint(hintId);
        Long cypherId = hint.getCypherId();
        hintService.deleteById(hintId);
        return REDIRECT_HINT_LIST + "?cypherId=" + cypherId;
    }

    @GetMapping(value = "/update/{id}")
    public String addForm(@PathVariable Long id, Model model) {
        Hint hint = hintService.getHint(id);
        model.addAttribute("heading","Upravit hint");
        model.addAttribute("hint", hint);
        return VIEW_HINT;
    }

    @GetMapping(value = "/new")
    public String newHint(@RequestParam("cypherId")Long cypherId, Model model) {
        Hint hint = cypherService.addHint(cypherId);
        model.addAttribute("heading","Nový hint");
        model.addAttribute("hint", hint);
        return VIEW_HINT;
    }

    @PostMapping(value = "/save")
    public String save(@Valid Hint hint, Model model) {
        Hint hint1 = hintService.save(hint);
        return REDIRECT_HINT_LIST + "?cypherId=" + hint1.getCypherId();
    }


    @ExceptionHandler({ ResourceNotFoundException.class})
    public void handleException() {
        /*
        TODO:
         */
    }
}
