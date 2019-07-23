package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.dto.CypherFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.UserService;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Primary
public class CypherModelMapperWrapper extends ModelMapperWrapper {

    private final UserService userService;

    @Autowired
    public CypherModelMapperWrapper(final UserService userService) {
        this.userService = userService;
        skipOrganizersMappingToDTO();
    }

    @Override
    public void map(final Object source, final Object destination) {
        super.getModelMapper().map(source, destination);
        if (destination.getClass() == Cypher.class) {
            mapOrganizersToCypher((CypherFormDTO) source, (Cypher) destination);
        } else if (destination.getClass() == CypherFormDTO.class) {
            mapOrganizerIdsToCypherFormDTO((Cypher) source, (CypherFormDTO) destination);
        } else {
            throw new IllegalArgumentException("Use only Cypher or CypherFormDTO!");
        }
    }

    @Override
    public <T> T map(final Object source, final Class<T> destinationClass) {
        if (destinationClass == Cypher.class) {
            Cypher cypher = super.getModelMapper().map(source, Cypher.class);
            mapOrganizersToCypher((CypherFormDTO) source, cypher);
            return (T) cypher;
        } else if (destinationClass == CypherFormDTO.class) {
            CypherFormDTO cypherFormDto = super.getModelMapper().map(source, CypherFormDTO.class);
            mapOrganizerIdsToCypherFormDTO((Cypher) source, cypherFormDto);
            return (T) cypherFormDto;
        } else {
            throw new IllegalArgumentException("Use only Cypher or CypherFormDTO!");
        }
    }

    private void mapOrganizersToCypher(final CypherFormDTO cypherFormDto, final Cypher cypher) {
        cypher.setOrganizers(userService.getOrganizersByIds(cypherFormDto.getOrganizers()));
    }

    private void mapOrganizerIdsToCypherFormDTO(final Cypher cypher, final CypherFormDTO cypherFormDto) {
        cypherFormDto.setOrganizers(cypher.getOrganizers().stream()
                .map(User::getUserId)
                .collect(Collectors.toList()));
    }

    private void skipOrganizersMappingToDTO() {
        super.getModelMapper().addMappings(new PropertyMap<Cypher, CypherFormDTO>() {
            protected void configure() {
                skip().setOrganizers(null);
            }
        });
    }
}
