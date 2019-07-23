package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.dto.CypherFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperWrapper implements ObjectMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public ModelMapperWrapper() {
        skipOrganizersMappingToDTO();
    }

    @Override
    public void map(final Object source, final Object destination) {
        modelMapper.map(source, destination);
    }

    @Override
    public <T> T map(final Object source, final Class<T> destinationClassName) {
        return modelMapper.map(source, destinationClassName);
    }

    private void skipOrganizersMappingToDTO() {
        modelMapper.addMappings(new PropertyMap<Cypher, CypherFormDTO>() {
            protected void configure() {
                skip().setOrganizers(null);
            }
        });
    }
}