package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.service.CypherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin/cypher")
public class CypherController {
    private static final String VIEW_CYPHER_LIST = "cypher/list";
    private static final String VIEW_CYPHER_DETAIL = "cypher/form";
    private static final String REDIRECT_CYPHER_LIST = "redirect:/admin/cypher/";
    private static final Point DEFAULT_LOCATION = new Point(59.9090442, 10.7423389);

    private CypherService cypherService;

    @Autowired
    public CypherController(final CypherService cypherService) {
        this.cypherService = cypherService;
    }

    @GetMapping
    public String cyphers(final Model model) {
        model.addAttribute("cyphers", cypherService.getAll());
        return VIEW_CYPHER_LIST;
    }

    @GetMapping(value = "/add")
    public String newCypher(final Model model) {
        Cypher cypher = new Cypher();
        model.addAttribute("heading", "Nová šifra");
        model.addAttribute("cypher", cypher);
        return VIEW_CYPHER_DETAIL;
    }

    @GetMapping(value = "/update/{cypherId}")
    public String updateCypher(@PathVariable("cypherId") final Long cypherId, final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        model.addAttribute("heading", "Upravit šifru");
        model.addAttribute(cypher);
        return VIEW_CYPHER_DETAIL;
    }

    @PostMapping(value = "/add")
    public String saveCypher(@Valid final Cypher cypher, final Model model) {
        cypherService.save(cypher);
        return REDIRECT_CYPHER_LIST;
    }

    @GetMapping(value = "/delete/{cypherId}")
    public String deleteCypher(@PathVariable("cypherId") final Long cypherId) {
        cypherService.deleteById(cypherId);
        return REDIRECT_CYPHER_LIST;
    }
}
