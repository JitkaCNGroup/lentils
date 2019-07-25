package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.dto.CypherFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LentilsApplication.class)
@Transactional
public class CypherMapperTest {

    private ObjectGenerator generator;
    @Autowired
    private CypherMapper cypherMapper;

    @Before
    public void setUp() {
        generator = new ObjectGenerator();
    }

    @Test
    public void testConvertCypherToDTO() {
        Cypher cypher = generator.generateValidCypher();
        List<User> organizers = new ArrayList<>();
        User user = new User();
        user.setUserId(88L);
        organizers.add(user);
        cypher.setOrganizers(organizers);

        CypherFormDTO cypherFormDto = cypherMapper.map(cypher, CypherFormDTO.class);

        assertTrue(cypher.getName().equals(cypherFormDto.getName()));
        assertEquals(cypher.getOrganizers().get(0).getUserId(), cypherFormDto.getOrganizers().get(0));
    }

    @Test
    public void testConvertDTOtoCypher() {
        CypherFormDTO cypherFormDto = new CypherFormDTO();
        List<Long> organizerIds = new ArrayList<>();
        organizerIds.add(2L);
        cypherFormDto.setName("fromDtoName");
        cypherFormDto.setOrganizers(organizerIds);

        Cypher cypher = cypherMapper.map(cypherFormDto, Cypher.class);

        assertTrue(cypher.getName().equals(cypherFormDto.getName()));
        assertEquals(cypher.getOrganizers().get(0).getUserId(), cypherFormDto.getOrganizers().get(0));
    }
}