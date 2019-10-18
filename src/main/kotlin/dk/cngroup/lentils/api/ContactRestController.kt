package dk.cngroup.lentils.api

import dk.cngroup.lentils.dto.ContactFormDTO
import dk.cngroup.lentils.entity.Contact
import dk.cngroup.lentils.service.ContactService
import dk.cngroup.lentils.service.convertors.ModelMapperWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class ContactRestController @Autowired constructor (
        private val contactService: ContactService,
        private val modelMapperWrapper: ModelMapperWrapper
) {

    @GetMapping("/api/contact")
    fun getContact(): ContactFormDTO {
        val contact = contactService.contact
        val contactDto = ContactFormDTO()
        modelMapperWrapper.map(contact, contactDto)
        return contactDto
    }

    @PostMapping("/api/contact")
    fun saveContact(@Valid @RequestBody contactFormDTO: ContactFormDTO): ContactFormDTO {
        val contact = Contact()
        modelMapperWrapper.map(contactFormDTO, contact)
        contactService.save(contact)
        return contactFormDTO
    }
}
