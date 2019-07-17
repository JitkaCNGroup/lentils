package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.formEntity.TeamDTO;
import dk.cngroup.lentils.service.convertors.ModelMapperWrapper;
import dk.cngroup.lentils.service.convertors.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ModelMapperWrapperTest {
    
    @Test
    public void testConvertTeamToDTO() {
        ObjectMapper modelMapper = new ModelMapperWrapper();
        ObjectGenerator objectGenerator = new ObjectGenerator();
        Team team = objectGenerator.generateValidTeam();

        TeamDTO teamDto = modelMapper.map(team, TeamDTO.class);

        assertTrue(teamDto.getName().equals(team.getName()));
        assertTrue(teamDto.getNumOfMembers().equals(team.getNumOfMembers()));
    }

    @Test
    public void testUpdateTeamWithDTO() {
        ObjectMapper modelMapper = new ModelMapperWrapper();
        ObjectGenerator objectGenerator = new ObjectGenerator();
        Team team = objectGenerator.generateValidTeam();
        TeamDTO teamDto = new TeamDTO();
        teamDto.setName("general");
        teamDto.setNumOfMembers(8);

        modelMapper.map(teamDto, team);

        assertTrue(teamDto.getName().equals(team.getName()));
        assertTrue(teamDto.getNumOfMembers().equals(team.getNumOfMembers()));
    }
}
