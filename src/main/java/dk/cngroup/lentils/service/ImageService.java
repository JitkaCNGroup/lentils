package dk.cngroup.lentils.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    public static final String IMAGE_DIRECTORY = "\\images\\";

    public static File chooseFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Select an image");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PNG, JPG, JPEG, BMP and GIF images", "png", "jpg", "jpeg", "bmp", "gif");
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        }
        return null;
    }

    public static void readAndSaveImageFile(final File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Picture file cannot be open. " + e.getMessage());
        }
        String fileName = file.getName();
        String pathToSave = getDirectory() + fileName;
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            ImageIO.write(image, fileExtension, new File(pathToSave));
        } catch (IOException e) {
            System.err.println("Picture file cannot be saved. " + e.getMessage());
        }
    }

    private static String getDirectory() {
        String newDirectory = System.getProperty("user.dir") + IMAGE_DIRECTORY;
        File newDirectoryDir = new File(newDirectory);
        if (!newDirectoryDir.exists()) {
            newDirectoryDir.mkdir();
        }
        return newDirectory;
    }
}
