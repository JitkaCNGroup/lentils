package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.entity.formentity.CypherFormObject;
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

    public CypherFormObject fromEntity(final Cypher cypher) {
        final CypherFormObject formObject = new CypherFormObject();

        formObject.setName(cypher.getName());
        formObject.setStage(cypher.getStage());
        formObject.setLocation(cypher.getLocation());
        formObject.setMapAddress(cypher.getMapAddress());
        formObject.setCodeword(cypher.getCodeword());
        formObject.setTrapCodeword(cypher.getTrapCodeword());
        formObject.setBonusContent(cypher.getBonusContent());
        formObject.setPlaceDescription(cypher.getPlaceDescription());
        formObject.setOrganizers(getOrganizerIds(cypher.getOrganizers()));

        return formObject;
    }

    public void toEntity(final CypherFormObject command, final Cypher cypher) {
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
