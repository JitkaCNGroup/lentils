package dk.cngroup.lentils.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityType, Long entityId) {
        super(String.format("Resource of type '%s' with ID=%d not found", entityType, entityId));
    }
}
