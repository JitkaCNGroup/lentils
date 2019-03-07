package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/hint")
public class HintController {
    private static final String VIEW_HINT_LIST = "hint/list";
    private static final String VIEW_HINT = "hint/detail";
    private static final String REDIRECT_HINT_LIST = "redirect:/hint/list";

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

    @GetMapping(value = "/delete")
    public String deleteHint(@RequestParam("hintId") Long hintId) {
        Hint hint = hintService.getHint(hintId);
        Long cypherId = hint.getCypherId();
        hintService.deleteById(hintId);
        return REDIRECT_HINT_LIST + "?cypherId=" + cypherId;
    }

    @GetMapping(value = "/update")
    public String addForm(@RequestParam("hintId") Long hintId, Model model) {
        Hint hint = hintService.getHint(hintId);
        model.addAttribute("heading","Upravit hint");
        model.addAttribute("hint", hint);
        return VIEW_HINT;
    }

    @GetMapping(value = "/new")
    public String newHint(@RequestParam("cypherId") Long cypherId, Model model) {
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
}
