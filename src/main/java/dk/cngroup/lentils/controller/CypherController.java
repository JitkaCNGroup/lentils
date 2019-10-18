package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.dto.CypherFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.UserService;
import dk.cngroup.lentils.service.convertors.CypherMapper;
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
    private static final String VIEW_ADMIN_CYPHER_LIST = "cypher/list";
    private static final String VIEW_ADMIN_CYPHER_FORM = "cypher/form";
    private static final String REDIRECT_ADMIN_CYPHER_LIST = "redirect:/admin/cypher/";
    private static final String ACTION_ADD = "add";
    private static final String ACTION_UPDATE = "update";

    private static final String TEMPLATE_ATTR_CYPHERS = "cyphers";
    private static final String TEMPLATE_ATTR_COMMAND = "command";
    private static final String TEMPLATE_ATTR_ALL_ORGANIZERS = "allOrganizers";
    private static final String TEMPLATE_ATTR_ACTION = "action";

    private final CypherService cypherService;
    private final UserService userService;
    private final CypherMapper cypherMapper;

    @Autowired
    public CypherController(final CypherService cypherService,
                            final UserService userService,
                            final CypherMapper cypherMapper) {
        this.cypherService = cypherService;
        this.userService = userService;
        this.cypherMapper = cypherMapper;
    }

    @GetMapping
    public String cyphers(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_CYPHERS, cypherService.getAllCyphersOrderByStageAsc());
        return VIEW_ADMIN_CYPHER_LIST;
    }

    @GetMapping(value = "/add")
    public String newCypher(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_COMMAND, new CypherFormDTO());
        model.addAttribute(TEMPLATE_ATTR_ALL_ORGANIZERS, userService.getOrganizers());
        model.addAttribute(TEMPLATE_ATTR_ACTION, ACTION_ADD);
        return VIEW_ADMIN_CYPHER_FORM;
    }

    @PostMapping(value = "/add")

    public String saveNewCypher(@Valid @ModelAttribute(TEMPLATE_ATTR_COMMAND) final CypherFormDTO command,
                                final BindingResult bindingResult,
                                final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ACTION, ACTION_ADD);
            return VIEW_ADMIN_CYPHER_FORM;
        }

        final Cypher cypher = cypherMapper.map(command, Cypher.class);
        cypherService.save(cypher);

        return REDIRECT_ADMIN_CYPHER_LIST;
    }

    @GetMapping(value = "/update/{cypherId}")
    public String updateCypherForm(@PathVariable("cypherId") final Long cypherId, final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        final CypherFormDTO cypherFormDto = cypherMapper.map(cypher, CypherFormDTO.class);
        model.addAttribute(TEMPLATE_ATTR_COMMAND, cypherFormDto);
        model.addAttribute(TEMPLATE_ATTR_ALL_ORGANIZERS, userService.getOrganizers());
        model.addAttribute(TEMPLATE_ATTR_ACTION, ACTION_UPDATE);
        return VIEW_ADMIN_CYPHER_FORM;
    }

    @PostMapping(value = "/update/{cypherId}")
    public String updateCypher(@PathVariable("cypherId") final Long cypherId,
                               @Valid @ModelAttribute(TEMPLATE_ATTR_COMMAND) final CypherFormDTO command,
                               final BindingResult bindingResult,
                               final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_ACTION, ACTION_UPDATE);
            return VIEW_ADMIN_CYPHER_FORM;
        }

        final Cypher cypher = cypherService.getCypher(cypherId);
        cypherMapper.map(command, cypher);
        cypherService.save(cypher);

        return REDIRECT_ADMIN_CYPHER_LIST;
    }

    @GetMapping(value = "/delete/{cypherId}")
    public String deleteCypher(@PathVariable("cypherId") final Long cypherId) {
        cypherService.deleteById(cypherId);
        return REDIRECT_ADMIN_CYPHER_LIST;
    }
}
