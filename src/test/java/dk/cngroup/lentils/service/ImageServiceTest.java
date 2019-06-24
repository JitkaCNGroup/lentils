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

}