package dk.cngroup.lentils.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) {
        super(msg + " not found.");
    }
}
