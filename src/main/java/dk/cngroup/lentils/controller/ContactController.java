package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.dto.ContactFormDTO;
import dk.cngroup.lentils.entity.Contact;
import dk.cngroup.lentils.service.ContactService;
import dk.cngroup.lentils.service.convertors.ModelMapperWrapper;
import dk.cngroup.lentils.service.convertors.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/contact")
public class ContactController {
    private static final String VIEW_ADMIN_CONTACT_FORM = "contact/form";
    private static final String REDIRECT_ADMIN_CONTACT = "redirect:/admin/contact/";

    private static final String TEMPLATE_ATTR_CONTACT = "contact";

    private final ContactService contactService;
    private final ObjectMapper mapper;

    @Autowired
    public ContactController(final ContactService contactService,
                             final ModelMapperWrapper modelMapperWrapper) {
        this.contactService = contactService;
        this.mapper = modelMapperWrapper;
    }

    @GetMapping(value = "/")
    public String contact(final Model model) {
        final Contact contact = contactService.getContact();
        final ContactFormDTO contactDto = new ContactFormDTO();
        mapper.map(contact, contactDto);
        model.addAttribute(TEMPLATE_ATTR_CONTACT, contactDto);

        return VIEW_ADMIN_CONTACT_FORM;
    }

    @PostMapping(value = "/update")
    public String saveContact(@Valid @ModelAttribute(TEMPLATE_ATTR_CONTACT) final ContactFormDTO contactDTO,
                              final BindingResult bindingResult,
                              final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_CONTACT, contactDTO);
            return VIEW_ADMIN_CONTACT_FORM;
        }

        final Contact contact = contactService.getContact();
        mapper.map(contactDTO, contact);
        contactService.save(contact);

        return REDIRECT_ADMIN_CONTACT;
    }
}
