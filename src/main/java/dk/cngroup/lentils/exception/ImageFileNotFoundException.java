package dk.cngroup.lentils.exception;

public class ImageFileNotFoundException extends RuntimeException {
    public ImageFileNotFoundException() {
        super("Image file not found");
    }
}
