package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Contact;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.exception.MoreContactsException;
import dk.cngroup.lentils.exception.MoreFinalPlacesException;
import dk.cngroup.lentils.repository.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    @InjectMocks
    ContactService service;

    @Mock
    ContactRepository repository;

    private ObjectGenerator generator = new ObjectGenerator();

    @Test
    public void addNewContactTest() {
        Contact contact = createNewContact();
        when(repository.save(contact)).thenReturn(contact);

        assertNotNull(contact);
        assertEquals(contact, service.save(contact));
    }

    @Test
    public void addTwoContactsTest() {
        Contact contact = createNewContact();
        when(repository.save(contact)).thenReturn(contact);

        assertNotNull(contact);
        assertEquals(contact, service.save(contact));
    }

    @Test
    public void deleteContactTest() {
        Contact contact = createNewContact();
        when(repository.count()).thenReturn(0L);

        service.deleteAll();

        assertEquals(0, repository.count());
    }

    @Test
    public void testGetContact() {
        final Contact contact = new Contact();
        final List<Contact> list = new ArrayList<>();
        list.add(contact);
        when(repository.findAll()).thenReturn(list);

        Contact result = service.getContact();

        assertEquals(contact, result);
    }

    @Test(expected = MoreContactsException.class)
    public void testGetFinalPlaceWithMultiplePlacesSet() {
        final List<Contact> list = new ArrayList<>();
        list.add(new Contact());
        list.add(new Contact());
        when(repository.findAll()).thenReturn(list);

        service.getContact();
    }

    private Contact createNewContact() {
        Contact contact = generator.generateContact();
        when(repository.save(contact)).thenReturn(contact);
        return service.save(contact);
    }
}
