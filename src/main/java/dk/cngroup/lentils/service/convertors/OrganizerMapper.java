package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.dto.OrganizerFormDTO;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.CypherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrganizerMapper extends ModelMapperWrapper {

    private final CypherService cypherService;

    @Autowired
    public OrganizerMapper(final CypherService cypherService) {
        this.cypherService = cypherService;
    }

    public OrganizerFormDTO map(final User organizer) {
        OrganizerFormDTO organizerFormDto = new OrganizerFormDTO();
        super.getModelMapper().map(organizer, organizerFormDto);
        organizerFormDto.setCyphers(cypherService.getAllWithOrganizer().stream()
                .filter(cypher -> cypher.getOrganizers()
                        .contains(organizer))
                .collect(Collectors.toList()));
        return organizerFormDto;
    }
}
