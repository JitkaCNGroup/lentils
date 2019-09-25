package dk.cngroup.lentils.exception;

public class FileNotDeletedException extends RuntimeException {
    public FileNotDeletedException() {
        super("File not deleted");
    }
}
