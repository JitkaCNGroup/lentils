package dk.cngroup.lentils.service.convertors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperWrapper implements ObjectMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Override
    public void map(final Object source, final Object destination) {
        modelMapper.map(source, destination);
    }

    @Override
    public <T> T map(final Object source, final Class<T> destinationClassName) {
        return modelMapper.map(source, destinationClassName);
    }
}