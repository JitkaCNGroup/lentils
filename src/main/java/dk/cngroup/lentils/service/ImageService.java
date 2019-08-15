package dk.cngroup.lentils.service;

import dk.cngroup.lentils.config.ConfigProperties;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.exception.FileNotFoundException;
import dk.cngroup.lentils.exception.FileNotSavedException;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.ImageRepository;
import dk.cngroup.lentils.util.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final ConfigProperties configProp;

    public ImageService(final ImageRepository imageRepository, final ConfigProperties configProp) {
        this.imageRepository = imageRepository;
        this.configProp = configProp;
    }

    public Image save(final Image image) {
        return imageRepository.save(image);
    }

    public String getImageDirectory() {
        return configProp.getConfigValue("imageDirectory");
    }

    public String getDirectory() {
        String newDirectory = FileUtils.getUserDir() + getImageDirectory();
        FileUtils.createDirectory(newDirectory);
        return newDirectory;
    }

    public void saveImageFile(final HintFormDTO formObject) {
        if (!FileUtils.isFilePresentInHintForm(formObject)) {
            return;
        }
        MultipartFile file = formObject.getImage();
        String pathToSave = getFilePathToSave(file);
        File dest = new File(pathToSave);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileNotSavedException();
        }
    }

    public String getFilePathToShow(final String fileName) {
        return getDirectory() + fileName;
    }

    public String getFilePath(final MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        }
        String fileName = file.getOriginalFilename();
        return getImageDirectory() + fileName;
    }

    public Path load(final String path) {
        return Paths.get(path);
    }

    public Resource loadAsResource(final String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException();
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException();
        }
    }

    public String getFilePathToSave(final MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        }
        String fileName = file.getOriginalFilename();
        FileUtils.createDirectory(getDirectory());
        return getDirectory() + fileName;
    }

    public Image getImage(final Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException(
                Image.class.getSimpleName(), imageId));
    }

    public Image getImageFromMultipartFile(final MultipartFile file) {
        return new Image(getFilePath(file));
    }

}
