package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.service.CypherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cypher")
public class CypherController {
    private static final Logger LOGGER = LoggerFactory.getLogger(dk.cngroup.lentils.controller.CypherController.class);

    private CypherService cypherService;

    @Autowired
    public CypherController(CypherService cypherService) {
        this.cypherService = cypherService;
    }

    @GetMapping
    public String cyphers(Model model) {
        model.addAttribute("cyphers", cypherService.getAll());
        return "cypher.list";
    }

    @PostMapping(value = "/add")
    public String addCypher(Cypher cypher, Model model) {
        cypherService.add(cypher);
        model.addAttribute("cyphers", cypherService.getAll());
        return "redirect:/cypher.list";
    }

    @DeleteMapping(value = "/del/{id}")
    public String deleteCypher(@PathVariable Long id, Model model) {
        cypherService.deleteById(id);
        model.addAttribute("cyphers", cypherService.getAll());
        return "redirect:/cypher.list";
    }

    @DeleteMapping(value = "/del")
    public String deleteCypher(Model model) {
        cypherService.deleteAll();
        model.addAttribute("cyphers", cypherService.getAll());
        return "redirect:/cypher.list";
    }
}
