package dk.cngroup.lentils.exception;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException() {
        super("File not found");
    }
}
