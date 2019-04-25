package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Contact;
import dk.cngroup.lentils.exception.MoreContactsException;
import dk.cngroup.lentils.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactService(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact save(final Contact contact) {
        final Contact newContact = getContact();
        contact.setContactId(newContact.getContactId());

        return contactRepository.save(contact);
    }

    /**
     * Get contact entity.
     *
     * assumption:
     * there is only one line in the table
     */
    public Contact getContact() {
        List<Contact> contacts = contactRepository.findAll();
        if (contacts.size() == 0) {
            return new Contact();
        } else if (contacts.size() == 1) {
            return contacts.get(0);
        }

        throw new MoreContactsException("More final places found.");
    }

    /**
     * Delete all contacts.
     *
     * assumption:
     * there is only one line in the table
     */
    public void deleteAll() {
        contactRepository.deleteAll();
    }
}
