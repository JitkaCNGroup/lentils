package dk.cngroup.lentils.service.convertors;

public interface ObjectMapper {
    void map(Object source, Object destination);
    <T>T map(Object source, Class<T> destinationClassName);
}