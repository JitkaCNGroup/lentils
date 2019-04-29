package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Contact;
import dk.cngroup.lentils.service.ContactService;
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
    private static final String VIEW_CONTACT_FORM = "contact/form";
    private static final String REDIRECT_CONTACT_FORM = "redirect:/admin/contact/";

    private ContactService contactService;

    @Autowired
    public ContactController(final ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(value = "/")
    public String contact(final Model model) {
        model.addAttribute("contact", contactService.getContact());
        return VIEW_CONTACT_FORM;
    }

    @PostMapping(value = "/update")
    public String saveContact(@Valid @ModelAttribute final Contact contact,
                                 final BindingResult bindingResult,
                                 final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contact", contact);
            return VIEW_CONTACT_FORM;
        }
        contactService.save(contact);
        return REDIRECT_CONTACT_FORM;
    }
}
