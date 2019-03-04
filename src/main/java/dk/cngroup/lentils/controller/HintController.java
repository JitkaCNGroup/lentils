package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.exception.CypherNotFoundException;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping(value = "/list/{cypherId}")
    public String list(@PathVariable Long cypherId,  Model model) throws CypherNotFoundException {
        Cypher cypher = cypherService.getCypher(cypherId);
        model.addAttribute("cypher", cypher);
        return VIEW_HINT_LIST;
    }

    @GetMapping(value = "/delete/{hintId}/{cypherId}")
    public String delete(@PathVariable Long hintId, @PathVariable Long cypherId) {
        hintService.deleteById(hintId);
        return REDIRECT_HINT_LIST + "/" + cypherId;
    }

    @GetMapping(value = "/add/{cypherId}")
    public String add(@PathVariable Long cypherId, Model model) throws CypherNotFoundException {
        Hint hint = cypherService.addHint(cypherId);
        model.addAttribute("hint", hint);
        return VIEW_HINT;
    }

    @PostMapping(value = "/save")
    public String save(@Valid Hint hint, Model model) throws CypherNotFoundException {
        Long cypherId = cypherService.saveHint(hint);
        return REDIRECT_HINT_LIST + "/" + cypherId;
    }
}
