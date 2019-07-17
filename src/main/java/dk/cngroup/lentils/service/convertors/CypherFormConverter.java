package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.dto.CypherFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CypherFormConverter {

    private UserService userService;

    @Autowired
    public void setUserService(final UserService userService) {
        this.userService = userService;
    }

    public CypherFormDTO fromEntity(final Cypher cypher) {
        final CypherFormDTO cypherFormDto = new CypherFormDTO();

        cypherFormDto.setName(cypher.getName());
        cypherFormDto.setStage(cypher.getStage());
        cypherFormDto.setLocation(cypher.getLocation());
        cypherFormDto.setMapAddress(cypher.getMapAddress());
        cypherFormDto.setCodeword(cypher.getCodeword());
        cypherFormDto.setTrapCodeword(cypher.getTrapCodeword());
        cypherFormDto.setBonusContent(cypher.getBonusContent());
        cypherFormDto.setPlaceDescription(cypher.getPlaceDescription());
        cypherFormDto.setOrganizers(getOrganizerIds(cypher.getOrganizers()));

        return cypherFormDto;
    }

    public void toEntity(final CypherFormDTO command, final Cypher cypher) {
        cypher.setName(command.getName());
        cypher.setName(command.getName());
        cypher.setStage(command.getStage());
        cypher.setLocation(command.getLocation());
        cypher.setMapAddress(command.getMapAddress());
        cypher.setCodeword(command.getCodeword());
        cypher.setTrapCodeword(command.getTrapCodeword());
        cypher.setBonusContent(command.getBonusContent());
        cypher.setPlaceDescription(command.getPlaceDescription());
        cypher.setOrganizers(userService.getOrganizersByIds(command.getOrganizers()));
    }

    private List<Long> getOrganizerIds(final List<User> organizers) {
        return organizers.stream()
                .map(User::getUserId)
                .collect(Collectors.toList());
    }
}
