package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.formEntity.TeamDTO;
import org.springframework.stereotype.Service;

@Service
public class TeamConverter {

    public TeamDTO fromEntity(final Team team) {
        final TeamDTO dto = new TeamDTO();

        dto.setName(team.getName());
        dto.setNumOfMembers(team.getNumOfMembers());

        return dto;
    }

    public void toEntity(final TeamDTO dto, final Team team) {
        team.setName(dto.getName());
        team.setNumOfMembers(dto.getNumOfMembers());
    }
}
