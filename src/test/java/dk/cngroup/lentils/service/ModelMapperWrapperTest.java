package dk.cngroup.lentils.service;

import dk.cngroup.lentils.dto.TeamFormDTO;
import dk.cngroup.lentils.entity.Team;
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

        TeamFormDTO teamFormDto = modelMapper.map(team, TeamFormDTO.class);

        assertTrue(teamFormDto.getName().equals(team.getName()));
        assertTrue(teamFormDto.getNumOfMembers().equals(team.getNumOfMembers()));
    }

    @Test
    public void testUpdateTeamWithDTO() {
        ObjectMapper modelMapper = new ModelMapperWrapper();
        ObjectGenerator objectGenerator = new ObjectGenerator();
        Team team = objectGenerator.generateValidTeam();
        TeamFormDTO teamFormDto = new TeamFormDTO();
        teamFormDto.setName("general");
        teamFormDto.setNumOfMembers(8);

        modelMapper.map(teamFormDto, team);

        assertTrue(teamFormDto.getName().equals(team.getName()));
        assertTrue(teamFormDto.getNumOfMembers().equals(team.getNumOfMembers()));
    }
}
