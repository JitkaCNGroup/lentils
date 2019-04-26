package dk.cngroup.lentils.exception;

public class NotOneHourBeforeOpeningTimeException extends RuntimeException {
    public NotOneHourBeforeOpeningTimeException() {
        super("Wait for the right time...");
    }
}
