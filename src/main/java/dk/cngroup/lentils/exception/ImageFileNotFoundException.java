package dk.cngroup.lentils.exception;

public class ImageFileNotFoundException extends RuntimeException {
    public ImageFileNotFoundException() {
        super(String.format("Image file not found"));
    }
}
