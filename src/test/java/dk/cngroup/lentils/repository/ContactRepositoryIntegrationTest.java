package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Contact;
import dk.cngroup.lentils.service.ContactService;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class ContactRepositoryIntegrationTest {

    @Autowired
    ContactService contactService;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    ObjectGenerator objectGenerator;

    @Test
    public void saveContactCorrectlyTest() {
        long count = contactRepository.count();
        Contact contact = objectGenerator.generateContact();
        contactRepository.saveAndFlush(contact);

        Assert.assertEquals(count + 1, contactRepository.count());
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void saveContactWithEmptyPhone() {
        Contact contact = objectGenerator.generateContact();
        contact.setPhoneNumber("");
        contactRepository.saveAndFlush(contact);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void saveContactWithEmptyName() {
        Contact contact = objectGenerator.generateContact();
        contact.setName("");
        contactRepository.saveAndFlush(contact);
    }
}
