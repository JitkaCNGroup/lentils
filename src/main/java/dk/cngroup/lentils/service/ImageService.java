package dk.cngroup.lentils.service;

import dk.cngroup.lentils.config.ConfigProperties;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.exception.FileNotFoundException;
import dk.cngroup.lentils.exception.FileNotSavedException;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.ImageRepository;
import dk.cngroup.lentils.util.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ImageService {

    private static final int HASH_LENGTH = 4;

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

    public void saveImageFile(final HintFormDTO formObject, final String path) {
        if (!FileUtils.isFilePresentInHintForm(formObject)) {
            return;
        }
        MultipartFile file = formObject.getImage();
        File dest = new File(FileUtils.getUserDir() + path);
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
        String fileName = getFileName(file);
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

    private String getFileName(final MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        }
        return addUniqueSuffixToFilename(file.getOriginalFilename());
    }

    public String addUniqueSuffixToFilename(final String filename) {
        String uniqueSuffix = generateUniqueString();
        int i = filename.lastIndexOf('.');
        if (i < 0) {
           return StringUtils.cleanPath(filename + uniqueSuffix);
        } else {
            return StringUtils.cleanPath(filename.substring(0, i)
                    + uniqueSuffix
                    + filename.substring(i));
        }
    }

    private String generateUniqueString() {
        return String.format("-%tF-%s", new Date(), RandomStringUtils.randomAlphanumeric(HASH_LENGTH));
    }

    public String getPureFileName(final String filename) {
        int i = filename.lastIndexOf('/');
        return StringUtils.cleanPath((filename.substring(i + 1)));
    }

    public Image getImage(final Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException(
                Image.class.getSimpleName(), imageId));
    }

    public Image getImageFromMultipartFile(final MultipartFile file) {
        return new Image(getFilePath(file));
    }
}
