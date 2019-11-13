package dk.cngroup.lentils.service;

import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.exception.FileNotFoundException;
import dk.cngroup.lentils.exception.FileNotSavedException;
import dk.cngroup.lentils.util.FileTreatingUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
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
public class ImageManipulationLocalImpl implements ImageManipulation {

    private static final int HASH_LENGTH = 4;
    private static String imageDirectory;

    @Value("${imageDirectory}")
    public void setImageDirectory(final String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    @Override
    public Image getProperImage(final HintFormDTO hintFormDTO, final Image actualImage) {
        if (hintFormDTO.getImageFile().isEmpty()) {
            return actualImage;
        }
        if (actualImage == null) {
            return createNewLocalImage(hintFormDTO.getImageFile());
        }
        return setNewFileToLocalImage(hintFormDTO.getImageFile(), actualImage);
    }

    @NotNull
    private Image setNewFileToLocalImage(final MultipartFile file, final Image actualImage) {
        FileTreatingUtils.deleteImageFile(actualImage);
        setImageFromMultipartFile(file, actualImage);
        saveFile(actualImage.getImageUrl(), file);
        return actualImage;
    }

    @NotNull
    private Image createNewLocalImage(final MultipartFile file) {
        Image image;
        image = createImageFromMultipartFile(file);
        saveFile(image.getImageUrl(), file);
        return image;
    }

    private Image createImageFromMultipartFile(final MultipartFile file) {
        return new Image(getFilePath(file));
    }

    public String getFilePath(final MultipartFile file) {
        String fileName = getFileName(file);
        return getImageDirectory() + fileName;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public String getDirectory() {
        String newDirectory = FileTreatingUtils.getUserDir() + getImageDirectory();
        FileTreatingUtils.createDirectory(newDirectory);
        return newDirectory;
    }

    private void saveFile(final String path, final MultipartFile file) {
        File dest = new File(FileTreatingUtils.getUserDir() + path);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileNotSavedException();
        }
    }

    public String getFilePathToShow(final String fileName) {
        return getDirectory() + fileName;
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
            }
            throw new FileNotFoundException();
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

    private String addUniqueSuffixToFilename(final String filename) {
        String uniqueSuffix = generateUniqueString();
        int i = filename.lastIndexOf('.');
        if (i < 0) {
            return StringUtils.cleanPath(filename + uniqueSuffix);
        }
        return StringUtils.cleanPath(filename.substring(0, i)
                + uniqueSuffix
                + filename.substring(i));
    }

    private String generateUniqueString() {
        return String.format("-%tF-%s", new Date(), RandomStringUtils.randomAlphanumeric(HASH_LENGTH));
    }

    public String getPureFileName(final String filename) {
        int i = filename.lastIndexOf('/');
        return StringUtils.cleanPath((filename.substring(i + 1)));
    }

    private void setImageFromMultipartFile(final MultipartFile file, final Image image) {
        image.setImageUrl(getFilePath(file));
        image.setLocal(true);
    }
}
