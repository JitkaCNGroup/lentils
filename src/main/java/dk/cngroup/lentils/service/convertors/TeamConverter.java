package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.formEntity.TeamDTO;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamConverter {
    private TeamService teamService;

    @Autowired
    public TeamConverter(final TeamService teamService) {
        this.teamService = teamService;
    }

    public TeamDTO fromEntity(final Team team) {
        final TeamDTO dto = new TeamDTO();

        dto.setTeamId(team.getTeamId());
        dto.setName(team.getName());
        dto.setNumOfMembers(team.getNumOfMembers());
        dto.setPin(team.getPin());
        dto.setUser(team.getUser());

        return dto;
    }

    public void toEntity(final TeamDTO dto, final Team team) {
        team.setTeamId(dto.getTeamId());
        team.setName(dto.getName());
        team.setNumOfMembers(dto.getNumOfMembers());
        team.setPin(dto.getPin());
        team.setUser(dto.getUser());
    }

    public List<TeamDTO> getDtosListFromAllTeams() {
        List<TeamDTO> dtos = new ArrayList<>();
        teamService.getAll().forEach(team -> dtos.add(fromEntity(team)));
        return dtos;
    }
}
