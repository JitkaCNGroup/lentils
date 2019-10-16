package dk.cngroup.lentils.service;

import dk.cngroup.lentils.config.ConfigProperties;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.ImageSource;
import dk.cngroup.lentils.exception.FileNotDeletedException;
import dk.cngroup.lentils.exception.FileNotFoundException;
import dk.cngroup.lentils.exception.FileNotSavedException;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.ImageRepository;
import dk.cngroup.lentils.util.FileTreatingUtils;
import org.apache.commons.io.FileUtils;
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
import java.util.Optional;

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
        String newDirectory = FileTreatingUtils.getUserDir() + getImageDirectory();
        FileTreatingUtils.createDirectory(newDirectory);
        return newDirectory;
    }

    public void saveImageFile(final HintFormDTO formObject, final String path) {
        if (!FileTreatingUtils.isFilePresentInHintForm(formObject)) {
            return;
        }
        MultipartFile file = formObject.getImageFile();
        saveFile(path, file);
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

    public String addUniqueSuffixToFilename(final String filename) {
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

    public Image getImage(final Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException(
                Image.class.getSimpleName(), imageId));
    }

    public void setImageFromMultipartFile(final MultipartFile file, final Image image) {
        image.setImageUrl(getFilePath(file));
        image.setFromFile(true);
    }

    public Image createImageFromMultipartFile(final MultipartFile file) {
        return new Image(getFilePath(file));
    }

    public void deleteImage(final Image image) {
        if (image == null) {
            return;
        }
        imageRepository.deleteById(image.getImageId());
        deleteImageFile(image);
    }

    public void deleteImageFile(final Image image) {
        if (image != null && image.isFromFile() && image.getImageUrl() != "") {
            try {
                FileUtils.forceDelete(FileUtils.getFile(FileTreatingUtils.getUserDir() + image.getImageUrl()));
            } catch (IOException e) {
                throw new FileNotDeletedException();
            }
        }
    }

    public String getImageUrlForHintDto(final Hint hint) {
        return hint.getImage() == null || hint.getImage().isFromFile() ? "" : hint.getImage().getImageUrl();
    }

    public ImageSource getImageSource(final Hint hint) {
       if (!Optional.ofNullable(hint.getImage()).isPresent()) {
           return ImageSource.NONE;
       }
       if (hint.getImage().isFromFile()) {
           return ImageSource.FILE;
       }
       return ImageSource.WEB;
    }

    public Image generateImageFromDto(final String imageUrl,
                                      final ImageSource imageSource,
                                      final MultipartFile file,
                                      final Image actualImage) {
        Image image = null;
        switch (imageSource) {
            case WEB:
                if (actualImage == null) {
                    return new Image(imageUrl, false);
                }
                deleteImageFile(actualImage);
                actualImage.setImageUrl(imageUrl);
                actualImage.setFromFile(false);
                return actualImage;
            case FILE:
                if (file.isEmpty()) {
                    return actualImage;
                }
                if (actualImage == null) {
                    image = createImageFromMultipartFile(file);
                    saveFile(image.getImageUrl(), file);
                    return image;
                }
                deleteImageFile(actualImage);
                setImageFromMultipartFile(file, actualImage);
                saveFile(actualImage.getImageUrl(), file);
                return actualImage;
            default:
                return image;
        }
    }
}