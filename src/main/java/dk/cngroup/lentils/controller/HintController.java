package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/hint")
public class HintController {
    private static final String VIEW_HINT_LIST = "hint/list";
    private static final String VIEW_HINT = "hint/detail";
    private static final String REDIRECT_HINT_LIST = "redirect:/admin/hint";

    private static final String TEMPLATE_ATTR_CYPHER = "cypher";
    private static final String TEMPLATE_ATTR_HINT = "hint";
    private static final String TEMPLATE_ATTR_HEADING = "heading";

    private CypherService cypherService;
    private HintService hintService;

    @Autowired
    public HintController(final CypherService cypherService, final HintService hintService) {
        this.cypherService = cypherService;
        this.hintService = hintService;
    }

    @GetMapping(value = "")
    public String hints(@RequestParam("cypherId") final Long cypherId, final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        model.addAttribute(TEMPLATE_ATTR_CYPHER, cypher);
        return VIEW_HINT_LIST;
    }

    @GetMapping(value = "/delete/{hintId}")
    public String deleteHint(@PathVariable("hintId") final Long hintId) {
        Hint hint = hintService.getHint(hintId);
        Long cypherId = hint.getCypherId();
        hintService.deleteById(hintId);
        return REDIRECT_HINT_LIST + "?cypherId=" + cypherId;
    }

    @GetMapping(value = "/update/{hintId}")
    public String addForm(@PathVariable("hintId") final Long hintId, final Model model) {
        Hint hint = hintService.getHint(hintId);
        model.addAttribute(TEMPLATE_ATTR_HEADING, "Upravit hint");
        model.addAttribute(TEMPLATE_ATTR_HINT, hint);
        return VIEW_HINT;
    }

    @GetMapping(value = "/add")
    public String newHint(@RequestParam("cypherId") final Long cypherId, final Model model) {
        Hint hint = cypherService.addHint(cypherId);
        model.addAttribute(TEMPLATE_ATTR_HEADING, "Nový hint");
        model.addAttribute(TEMPLATE_ATTR_HINT, hint);
        return VIEW_HINT;
    }

    @PostMapping(value = "/save")
    public String save(@Valid @ModelAttribute final Hint hint,
                       final BindingResult bindingResult,
                       final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_HEADING, getHintHeading(hint));
            model.addAttribute(TEMPLATE_ATTR_HINT, hint);
            return VIEW_HINT;
        }
            Hint hint1 = hintService.save(hint);
        return REDIRECT_HINT_LIST + "?cypherId=" + hint1.getCypherId();
    }

    private String getHintHeading(final Hint hint) {
        if (hint.getHintId() == null) {
            return "Nový hint";
        } else {
            return "Upravit hint";
        }
    }
}
