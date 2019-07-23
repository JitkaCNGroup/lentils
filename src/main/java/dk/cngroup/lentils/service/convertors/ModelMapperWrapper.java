package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.dto.CypherFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapperWrapper implements ObjectMapper {

    private UserService userService;

    @Autowired
    public ModelMapperWrapper(final UserService userService) {
        this.userService = userService;
    }

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public void map(final Object source, final Object destination) {
        modelMapper.map(source, destination);
    }

    @Override
    public <T> T map(final Object source, final Class<T> destinationClassName) {
        return modelMapper.map(source, destinationClassName);
    }

    @Override
    public Cypher map(final CypherFormDTO source) {
        Cypher cypher = map(source, Cypher.class);
        cypher.setOrganizers(userService.getOrganizersByIds(source.getOrganizers()));
        return cypher;
    }

    @Override
    public void map(final CypherFormDTO source, final Cypher destination) {
        modelMapper.map(source, destination);
        destination.setOrganizers(userService.getOrganizersByIds(source.getOrganizers()));
    }

    @Override
    public CypherFormDTO map(final Cypher source) {
        skipOrganizersMappingToDTO();
        CypherFormDTO cypherFormDto = modelMapper.map(source, CypherFormDTO.class);
        cypherFormDto.setOrganizers(getOrganizerIds(source.getOrganizers()));
        return cypherFormDto;
    }

    private List<Long> getOrganizerIds(final List<User> organizers) {
        return organizers.stream()
                .map(User::getUserId)
                .collect(Collectors.toList());
    }

    private void skipOrganizersMappingToDTO() {
        modelMapper.addMappings(new PropertyMap<Cypher, CypherFormDTO>() {
            protected void configure() {
                skip().setOrganizers(null);
            }
        });
    }
}