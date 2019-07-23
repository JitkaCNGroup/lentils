package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.dto.CypherFormDTO;
import dk.cngroup.lentils.dto.TeamFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.convertors.ModelMapperWrapper;
import dk.cngroup.lentils.service.convertors.ObjectMapper;
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
public class ModelMapperWrapperTest {

    private ObjectGenerator generator;
    private ObjectMapper modelMapper;

    @Autowired
    public void setModelMapper(final ModelMapperWrapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Before
    public void setUp() {
        generator = new ObjectGenerator();
    }

    @Test
    public void testConvertTeamToDTO() {
        Team team = generator.generateValidTeam();

        TeamFormDTO teamFormDto = modelMapper.map(team, TeamFormDTO.class);

        assertTrue(teamFormDto.getName().equals(team.getName()));
        assertTrue(teamFormDto.getNumOfMembers().equals(team.getNumOfMembers()));
    }

    @Test
    public void testUpdateTeamWithDTO() {
        Team team = generator.generateValidTeam();
        TeamFormDTO teamFormDto = new TeamFormDTO();
        teamFormDto.setName("general");
        teamFormDto.setNumOfMembers(8);

        modelMapper.map(teamFormDto, team);

        assertTrue(teamFormDto.getName().equals(team.getName()));
        assertTrue(teamFormDto.getNumOfMembers().equals(team.getNumOfMembers()));
    }

    @Test
    public void testConvertCypherToDTO() {
        Cypher cypher = generator.generateValidCypher();
        List<User> organizers = new ArrayList<>();
        User user = new User();
        user.setUserId(88L);
        organizers.add(user);
        cypher.setOrganizers(organizers);

        CypherFormDTO cypherFormDto = modelMapper.map(cypher);

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
        Cypher cypher = modelMapper.map(cypherFormDto);

        assertTrue(cypher.getName().equals(cypherFormDto.getName()));
        assertEquals(cypher.getOrganizers().get(0).getUserId(), cypherFormDto.getOrganizers().get(0));
    }
}
