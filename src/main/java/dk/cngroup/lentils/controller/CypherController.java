package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.service.CypherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/cypher")
public class CypherController {
    private static final String VIEW_CYPHER_LIST = "cypher/list";
    private static final String VIEW_CYPHER_DETAIL = "cypher/form";
    private static final String REDIRECT_CYPHER_LIST = "redirect:/cypher/";

    private CypherService cypherService;

    @Autowired
    public CypherController(CypherService cypherService) {
        this.cypherService = cypherService;
    }

    @GetMapping
    public String cyphers(Model model) {
        model.addAttribute("cyphers", cypherService.getAll());
        return VIEW_CYPHER_LIST;
    }

    @GetMapping(value = "/new")
    public String newCypher(Model model) {
        Cypher cypher = new Cypher();
        cypher.setLocation(new Point(59.9090442,10.7423389));
        model.addAttribute("heading","Nová šifra");
        model.addAttribute("cypher", cypher);
        return VIEW_CYPHER_DETAIL;
    }

    @GetMapping(value = "/update")
    public String updateCypher(@RequestParam("cypherId") Long cypherId, Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        model.addAttribute("heading","Upravit šifru");
        model.addAttribute(cypher);
        return VIEW_CYPHER_DETAIL;
    }

    @PostMapping(value = "/save")
    public String saveCypher(@Valid Cypher cypher, Model model) {
        cypherService.save(cypher);
        return REDIRECT_CYPHER_LIST;
    }

    @GetMapping(value = "/delete")
    public String deleteCypher(@RequestParam("cypherId") Long cypherId) {
        cypherService.deleteById(cypherId);
        return REDIRECT_CYPHER_LIST;
    }
}
