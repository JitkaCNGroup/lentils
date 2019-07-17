package dk.cngroup.lentils.exception;

public class ImageNotSavedException extends RuntimeException {
    public ImageNotSavedException() {
        super("Image file not found");
    }
}
