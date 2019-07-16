package dk.cngroup.lentils.service.convertors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperWrapper implements ObjectMapper {
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }

    @Override
    public <T>T map(Object source, Class<T> destinationClassName) {
        return modelMapper.map(source, destinationClassName);
    }
}