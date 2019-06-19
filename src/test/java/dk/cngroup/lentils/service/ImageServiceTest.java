package dk.cngroup.lentils.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImageServiceTest {

    static final String JPG_FILE_TO_OPEN = "\\src\\main\\resources\\static\\img\\billy.jpg";
    static final String JPG_FILE_NAME = "\\billy.jpg";

    @InjectMocks
    ImageService imageService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    //file to choose - "user.home" + \\src\\main\\resources\\static\\img\\billy.jpg"
    @Test
    public void chooseJpgFile() {
        String fileToOpen = System.getProperty("user.dir") + JPG_FILE_TO_OPEN;
        assertEquals(fileToOpen, ImageService.chooseFile().toString());
    }

    //cancel choosing file
    @Test(expected = NullPointerException.class)
    public void chooseFileCanceled() {
        ImageService.chooseFile().toString();
    }

    //file to choose - "user.home" + \\src\\main\\resources\\static\\img\\billy.jpg"
    @Test
    public void chooseAndReadAndSaveImageWhenDirecotoryNotExists() {
        File file = ImageService.chooseFile();
        String path = System.getProperty("user.dir") + ImageService.IMAGE_DIRECTORY;
        File newDirectory = new File(path);
        File newFile = new File(newDirectory + JPG_FILE_NAME);
        ImageService.readAndSaveImageFile(file);
        assertTrue(newDirectory.exists());
        assertTrue(newFile.exists());
    }

}