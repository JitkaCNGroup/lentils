package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/cypher")
public class CypherController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CypherController.class);

    private final String VIEW_CYPHER_LIST = "cypher/list";
    private final String VIEW_CYPHER_DETAIL = "cypher/detail";
    private final String REDIRECT_CYPHER_LIST = "redirect:/cypher/";

    private CypherService cypherService;
    private HintService hintService;

    @Autowired
    public CypherController(CypherService cypherService, HintService hintService) {
        this.cypherService = cypherService;
        this.hintService = hintService;
    }

    @GetMapping(value = "/")
    public String cyphers(Model model) {
        model.addAttribute("cyphers", cypherService.getAll());
        return VIEW_CYPHER_LIST;
    }

    @GetMapping(value = "/new")
    public String addForm(Model model) {
        Cypher cypher = new Cypher();
        cypher.setLocation(new Point(59.9090442,10.7423389));
        model.addAttribute(cypher);
        return VIEW_CYPHER_DETAIL;
    }

    @GetMapping(value = "/update/{id}")
    public String addForm(@PathVariable Long id,  Model model) {
        Cypher cypher = cypherService.findById(id);
        model.addAttribute(cypher);
        return VIEW_CYPHER_DETAIL;
    }

    @PostMapping(value = "/save")
    public String addCypher(@Valid Cypher cypher, Model model) {
        cypherService.save(cypher);
        return REDIRECT_CYPHER_LIST;
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCypher(@PathVariable Long id) {
        cypherService.deleteById(id);
        return REDIRECT_CYPHER_LIST;
    }
}
