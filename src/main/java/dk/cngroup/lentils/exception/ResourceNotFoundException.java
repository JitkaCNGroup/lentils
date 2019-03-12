package dk.cngroup.lentils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String entityType, final Long entityId) {
        super(String.format("Resource of type '%s' with ID=%d not found", entityType, entityId));
    }
}
