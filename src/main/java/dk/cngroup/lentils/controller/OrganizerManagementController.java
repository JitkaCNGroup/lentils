package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.dto.OrganizerFormDTO;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.OrganizerService;
import dk.cngroup.lentils.service.convertors.OrganizerMapper;
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
@RequestMapping("/admin/organizermanagement")
public class OrganizerManagementController {

    private static final String VIEW_ADMIN_ORGANIZERMANAGEMENT_LIST = "organizermanagement/list";
    private static final String VIEW_ADMIN_ORGANIZERMANAGEMENT_FORM = "organizermanagement/form";
    private static final String REDIRECT_ADMIN_ORGANIZERMANAGEMENT = "redirect:/admin/organizermanagement";
    private static final String HEADING_NEW_ORGANIZER = "Nový organizátor";
    private static final String ACTION_ADD = "add";
    private static final String ACTION_UPDATE = "update";

    private static final String TEMPLATE_ATTR_ORGANIZERS = "organizers";
    private static final String TEMPLATE_ATTR_HEADING = "heading";
    private static final String TEMPLATE_ATTR_COMMAND = "command";
    private static final String TEMPLATE_ATTR_CYPHERS = "cyphers";
    private static final String TEMPLATE_ATTR_ACTION = "action";

    private final OrganizerMapper organizerMapper;
    private final CypherService cypherService;
    private final OrganizerService organizerService;

    @Autowired
    public OrganizerManagementController(final OrganizerMapper organizerMapper,
                                         final CypherService cypherService,
                                         final OrganizerService organizerService) {
        this.organizerMapper = organizerMapper;
        this.cypherService = cypherService;
        this.organizerService = organizerService;
    }

    @GetMapping
    public String organizers(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_ORGANIZERS, organizerService.getOrganizerDtos());
        return VIEW_ADMIN_ORGANIZERMANAGEMENT_LIST;
    }

    @GetMapping(value = "/add")
    public String newOrganizer(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_NEW_ORGANIZER);
        model.addAttribute(TEMPLATE_ATTR_COMMAND, new OrganizerFormDTO());
        model.addAttribute(TEMPLATE_ATTR_CYPHERS, cypherService.getAll());
        model.addAttribute(TEMPLATE_ATTR_ACTION, ACTION_ADD);
        return VIEW_ADMIN_ORGANIZERMANAGEMENT_FORM;
    }

    @PostMapping(value = "/add")
    public String saveNewOrganizer(@Valid @ModelAttribute(TEMPLATE_ATTR_COMMAND)
                                       final OrganizerFormDTO organizerFormDto,
                                   final BindingResult bindingResult,
                                   final Model model) {
        organizerService.checkUsernameIsUnique(organizerFormDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_NEW_ORGANIZER);
            model.addAttribute(TEMPLATE_ATTR_ACTION, ACTION_ADD);
            return VIEW_ADMIN_ORGANIZERMANAGEMENT_FORM;
        }
        organizerService.saveOrganizer(organizerMapper.map(organizerFormDto, User.class),
                organizerFormDto.getCypherIds());
        return REDIRECT_ADMIN_ORGANIZERMANAGEMENT;
    }

    @GetMapping(value = "/update/{id}")
    public String updateOrganizerForm(@PathVariable("id") final Long id,
                                      final Model model) {
        User organizer = organizerService.getOrganizerById(id);
        OrganizerFormDTO organizerFormDto = organizerMapper.map(organizer);
        model.addAttribute(TEMPLATE_ATTR_COMMAND, organizerFormDto);
        model.addAttribute(TEMPLATE_ATTR_CYPHERS, cypherService.getAllNotAssignedToOrganizer(organizer));
        model.addAttribute(TEMPLATE_ATTR_ACTION, ACTION_UPDATE);
        return VIEW_ADMIN_ORGANIZERMANAGEMENT_FORM;
    }

    @PostMapping(value = "/update/{id}")
    public String updateOrganizer(@PathVariable("id") final Long id,
                                  @Valid @ModelAttribute(TEMPLATE_ATTR_COMMAND) final OrganizerFormDTO organizerFormDto,
                                  final BindingResult bindingResult,
                                  final Model model) {
        organizerService.checkUsernameIsUnique(organizerFormDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_NEW_ORGANIZER);
            model.addAttribute(TEMPLATE_ATTR_ACTION, ACTION_UPDATE);
            return VIEW_ADMIN_ORGANIZERMANAGEMENT_FORM;
        }
        User user = organizerService.getOrganizerById(id);
        organizerMapper.map(organizerFormDto, user);
        organizerService.saveOrganizer(organizerMapper.map(organizerFormDto, User.class),
                organizerFormDto.getCypherIds());
        return REDIRECT_ADMIN_ORGANIZERMANAGEMENT;
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") final Long id) {
        organizerService.delete(id);
        return REDIRECT_ADMIN_ORGANIZERMANAGEMENT;
    }
}
