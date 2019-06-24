package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.entity.formEntity.HintFormObject;
import dk.cngroup.lentils.exception.ImageFileNotFoundException;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${image-directory}")
    private String imageDirectory;

    private final ImageRepository imageRepository;

    public ImageService(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image save(final Image image) {
        return imageRepository.save(image);
    }

    public MultipartFile getFile(final String stringPath) {
        File file = new File(stringPath);
        Path path = Paths.get(stringPath);
        String name = file.getName();
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                name, "image", content);
        return result;
    }

    public String getFilePath(final MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImageFileNotFoundException();
        }
        String fileName = file.getOriginalFilename();
        return getDirectory() + fileName;
    }

    public void saveImageFile(final MultipartFile file) {
        String pathToSave = getFilePath(file);
        File dest = new File(pathToSave);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
               System.err.println("Picture file cannot be saved. " + e.getMessage());
        }
    }

    public String getFileName(final HintFormObject formObject) {
         if (isFilePresentInHintForm(formObject)) {
            return formObject.getImage().getOriginalFilename();
        }
        return "";
    }

    public boolean isFilePresentInHintForm(final HintFormObject formObject) {
        MultipartFile file = formObject.getImage();
        if (file.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isFilePresentInHintEntity(final Hint hint) {
        Optional<Image> file = Optional.ofNullable(hint.getImage());
        if (file.isPresent()) {
            return true;
        }
        return false;
    }

    private String getDirectory() {
        String newDirectory = System.getProperty("user.dir") + imageDirectory;
        File newDirectoryDir = new File(newDirectory);
        if (!newDirectoryDir.exists()) {
            newDirectoryDir.mkdirs();
            }
        return newDirectory;
    }

    @Transactional
    public void deleteById(final Long id) {
        imageRepository.deleteById(id);
    }

    public Image getImage(final Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isPresent()) {
            return image.get();
        }
        throw new ResourceNotFoundException(Image.class.getSimpleName(), imageId);
    }
}
