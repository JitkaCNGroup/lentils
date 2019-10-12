package dk.cngroup.lentils.dto;

import dk.cngroup.lentils.entity.Contact;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.service.convertors.ModelMapperWrapper;
import dk.cngroup.lentils.service.convertors.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContactFormDTOTest {

    private ObjectMapper mapper;
    private ObjectGenerator generator;

    @Before
    public void setup() {
        mapper = new ModelMapperWrapper();
        generator = new ObjectGenerator();
    }

    @Test
    public void testMapToDTO() {
        final Contact entity = generator.generateContact();
        final ContactFormDTO dto = new ContactFormDTO();

        mapper.map(entity, dto);

        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
    }

    @Test
    public void testMapToEntity() {
        final Contact entity = generator.generateContact();
        final ContactFormDTO dto = new ContactFormDTO();
        dto.setName("Random test name");
        dto.setEmail("Random test email");
        dto.setPhoneNumber("Random test phone");

        mapper.map(dto, entity);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
    }
}
