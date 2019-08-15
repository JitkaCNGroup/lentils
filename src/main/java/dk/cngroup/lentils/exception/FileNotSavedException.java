package dk.cngroup.lentils.exception;

public class FileNotSavedException extends RuntimeException {
    public FileNotSavedException() {
        super("File not saved");
    }
}
