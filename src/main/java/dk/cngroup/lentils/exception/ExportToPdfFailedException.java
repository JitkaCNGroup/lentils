package dk.cngroup.lentils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ExportToPdfFailedException extends RuntimeException {
    public ExportToPdfFailedException() {
        super("Export to PDF failed");
    }
}
