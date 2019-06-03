package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.formEntity.CypherFormObject;
import dk.cngroup.lentils.service.CypherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private static final String HEADING_ADD_CYPHER = "Nová šifra";
    private static final String HEADING_EDIT_CYPHER = "Upravit šifru";

    private CypherService cypherService;

    @Autowired
    public CypherController(final CypherService cypherService) {
        this.cypherService = cypherService;
    }

    @GetMapping
    public String cyphers(final Model model) {
        model.addAttribute("cyphers", cypherService.getAllCyphersOrderByStageAsc());
        return VIEW_CYPHER_LIST;
    }

    @GetMapping(value = "/add")
    public String newCypher(final Model model) {
        model.addAttribute("heading", HEADING_ADD_CYPHER);
        model.addAttribute("command", new CypherFormObject());
        return VIEW_CYPHER_DETAIL;
    }

    @PostMapping(value = "/add")
    public String saveNewCypher(@Valid @ModelAttribute("command") final CypherFormObject command,
                                final BindingResult bindingResult,
                                final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("heading", HEADING_ADD_CYPHER);
            return VIEW_CYPHER_DETAIL;
        }

        final Cypher cypher = new Cypher();
        CypherFormObject.toEntity(cypher, command);

        cypherService.save(cypher);

        return REDIRECT_CYPHER_LIST;
    }

    @GetMapping(value = "/update/{cypherId}")
    public String updateCypherForm(@PathVariable("cypherId") final Long cypherId, final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        final CypherFormObject formObject = CypherFormObject.fromEntity(cypher);

        model.addAttribute("heading", HEADING_EDIT_CYPHER);
        model.addAttribute("command", formObject);
        return VIEW_CYPHER_DETAIL;
    }

    @PostMapping(value = "/update/{cypherId}")
    public String updateCypher(@PathVariable("cypherId") final Long cypherId,
                               @Valid @ModelAttribute("command") final CypherFormObject command,
                               final BindingResult bindingResult,
                               final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("heading", HEADING_EDIT_CYPHER);
            return VIEW_CYPHER_DETAIL;
        }

        final Cypher cypher = cypherService.getCypher(cypherId);
        CypherFormObject.toEntity(cypher, command);

        cypherService.save(cypher);

        return REDIRECT_CYPHER_LIST;
    }

    @GetMapping(value = "/delete/{cypherId}")
    public String deleteCypher(@PathVariable("cypherId") final Long cypherId) {
        cypherService.deleteById(cypherId);
        return REDIRECT_CYPHER_LIST;
    }
}
