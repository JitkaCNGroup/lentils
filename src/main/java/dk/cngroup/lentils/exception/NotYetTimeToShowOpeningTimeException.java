package dk.cngroup.lentils.exception;

public class NotYetTimeToShowOpeningTimeException extends RuntimeException {
    public NotYetTimeToShowOpeningTimeException() {
        super("Wait for the right time...");
    }
}
