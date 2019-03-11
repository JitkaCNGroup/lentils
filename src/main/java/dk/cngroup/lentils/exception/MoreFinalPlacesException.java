package dk.cngroup.lentils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class MoreFinalPlacesException extends RuntimeException {
    public MoreFinalPlacesException(final String msg) {
        super(msg);
    }
}
