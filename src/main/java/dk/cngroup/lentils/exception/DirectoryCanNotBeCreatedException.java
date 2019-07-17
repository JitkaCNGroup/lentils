package dk.cngroup.lentils.exception;

public class DirectoryCanNotBeCreatedException extends SecurityException {
    public DirectoryCanNotBeCreatedException() {
        super("Directory can not be created");
    }
}
