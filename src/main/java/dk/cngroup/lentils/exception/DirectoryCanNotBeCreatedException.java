package dk.cngroup.lentils.exception;

public class DirectoryCanNotBeCreatedException extends RuntimeException {
    public DirectoryCanNotBeCreatedException() {
        super("Directory can not be created");
    }
}
