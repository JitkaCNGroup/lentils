package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.service.CypherService;
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

    @Autowired
    public CypherController(CypherService cypherService) {
        this.cypherService = cypherService;
    }

    @GetMapping(value = "/")
    public String cyphers(Model model) {
        model.addAttribute("cyphers", cypherService.getAll());
        return VIEW_CYPHER_LIST;
    }

    @GetMapping(value = "/new")
    public String newCypher(Model model) {
        Cypher cypher = new Cypher();
        cypher.setLocation(new Point(59.9090442,10.7423389));
        model.addAttribute("nadpis","Nová šifra");
        model.addAttribute("cypher", cypher);
        return VIEW_CYPHER_DETAIL;
    }

    @GetMapping(value = "/update/{id}")
    public String updateCypher(@PathVariable Long id, Model model) {
        Cypher cypher = cypherService.getCypher(id);
        model.addAttribute("nadpis","Upravit šifru");
        model.addAttribute(cypher);
        return VIEW_CYPHER_DETAIL;
    }

    @PostMapping(value = "/save")
    public String saveCypher(@Valid Cypher cypher, Model model) {
        cypherService.save(cypher);
        return REDIRECT_CYPHER_LIST;
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCypher(@PathVariable Long id) {
        cypherService.deleteById(id);
        return REDIRECT_CYPHER_LIST;
    }

    @ExceptionHandler({ ResourceNotFoundException.class})
    public void handleException() {
        /*
        TODO:
         */
    }
}
