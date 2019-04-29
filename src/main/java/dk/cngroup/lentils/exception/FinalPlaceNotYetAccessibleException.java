package dk.cngroup.lentils.exception;

public class FinalPlaceNotYetAccessibleException extends RuntimeException {
    public FinalPlaceNotYetAccessibleException() {
        super("Wait for the right time...");
    }
}
