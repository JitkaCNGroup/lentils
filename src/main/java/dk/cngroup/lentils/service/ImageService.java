package dk.cngroup.lentils.service;

import dk.cngroup.lentils.util.TextChooser;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    public File chooseFileWithFileDialog() {
        FileDialog dialog = new FileDialog((Frame) null, "Select bmp, png or jpg File with to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setFilenameFilter(new TextChooser());
        dialog.setVisible(true);
        String dir = dialog.getDirectory();
        String file = dialog.getFile();
        if (dir == null || file == null) {
            return null;
        }
        return new File(dir, file);
    }

    public void openImageFile(final String fileName) {
        try {
            BufferedImage myPicture = ImageIO.read(new File(fileName));
            ImageIO.write(myPicture, "BMP", new File("filename.bmp"));
        } catch (IOException e) {
            System.err.println("Picture file cannot be open. " + e.getMessage());
        }

    }
}
