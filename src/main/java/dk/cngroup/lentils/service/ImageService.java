package dk.cngroup.lentils.service;

import dk.cngroup.lentils.config.ConfigProperties;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.exception.DirectoryCanNotBeCreatedException;
import dk.cngroup.lentils.exception.ImageFileNotFoundException;
import dk.cngroup.lentils.exception.ImageNotSavedException;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.ImageRepository;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ImageService {

//    @Value("${imageDirectory}")
//    private static String imageDirectory;
//    private static final String SEPARATOR = File.separator;
//    private static final String IMAGE_DIRECTORY = SEPARATOR + "upload" + SEPARATOR + "images" + SEPARATOR;

//    @Value("${image-directory}")
//    private String imageDirectory;

    private final ImageRepository imageRepository;

    private final ConfigProperties configProp;

    public ImageService(final ImageRepository imageRepository, final ConfigProperties configProp) {
        this.imageRepository = imageRepository;
        this.configProp = configProp;
    }

    public Image save(final Image image) {
        return imageRepository.save(image);
    }

    public MultipartFile getFile(final String stringPath) {
        final File file = new File(stringPath);
        DiskFileItem fileItem = new DiskFileItem("file", "image",
                true, file.getName(), (int) file.length(), file.getParentFile());
        try {
            fileItem.getOutputStream();
        } catch (IOException e) {
            throw new ImageFileNotFoundException();
        }
        MultipartFile result = new CommonsMultipartFile(fileItem);
        return result;
    }

    public DiskFileItem getFileItem(final String stringPath) {
        final File file = new File(stringPath);
        DiskFileItem fileItem = new DiskFileItem("file", "image",
                true, file.getName(), (int) file.length(), file.getParentFile());
        try {
            fileItem.getOutputStream();
        } catch (IOException e) {
            throw new ImageFileNotFoundException();
        }
        return fileItem;
    }

    public String getFileNameFromEntity(final Hint hint) {
        return isFilePresentInHintEntity(hint) ? hint.getImage().getPath() : "";
    }

    public Long getFileIdFromEntity(final Hint hint) {
        return isFilePresentInHintEntity(hint) ? hint.getImage().getImageId() : null;
    }

    public String getFileName(final Hint hint, final HintFormDTO formObject) {
        if (isFilePresentInHintForm(formObject)) {
            return formObject.getImage().getOriginalFilename();
        }
            return getFileNameFromEntity(hint);
    }

    public void setImageToFormObject(final Hint hint, final HintFormDTO formObject) {
        if (isFilePresentInHintEntity(hint)
                && (!isFilePresentInHintForm(formObject))) {
            formObject.setImage(getFile(hint.getImage().getPath()));
        }
    }

    public String getFileNamefromFormObject(final HintFormDTO formObject) {
        return isFilePresentInHintForm(formObject) ? formObject.getImage().getOriginalFilename() : "";
    }

    public String getFilePathToSave(final MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImageFileNotFoundException();
        }
        String fileName = file.getOriginalFilename();
        createDirectory(getDirectory());
        return getDirectory() + fileName;
    }

    public String getFilePathToShow(final String fileName) {
        return getDirectory() + fileName;
    }

    public String getFilePath(final MultipartFile file) {
        if (file.isEmpty()) {
            throw new ImageFileNotFoundException();
        }
        String fileName = file.getOriginalFilename();
        return configProp.getConfigValue("imageDirectory") + fileName;
    }

    public Path load(final String path) {
        return Paths.get(path);
    }

    public Resource loadAsResource(final String filename) {
        System.out.println("jsem v loadAsResource, filename: " + filename);
        try {
            Path file = load(filename);
            System.out.println("Path file: " + file.toString());
            Resource resource = new UrlResource(file.toUri());
            System.out.println("resource: " + resource.toString());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ImageFileNotFoundException();
            }
        } catch (MalformedURLException e) {
            throw new ImageFileNotFoundException();
        }
    }

    public String getImageDirectory() {
        return configProp.getConfigValue("imageDirectory");
    }

    public void saveImageFile(final HintFormDTO formObject) {
        if (!isFilePresentInHintForm(formObject)) {
            return;
        }
        MultipartFile file = formObject.getImage();
        String pathToSave = getFilePathToSave(file);
        File dest = new File(pathToSave);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new ImageNotSavedException();
        }
    }

    private boolean isFilePresentInHintForm(final HintFormDTO formObject) {
        MultipartFile file = formObject.getImage();
        return !file.isEmpty();
    }

    private boolean isFilePresentInHintEntity(final Hint hint) {
        Optional<Image> file = Optional.ofNullable(hint.getImage());
        return file.isPresent();
    }

    public String getDirectory() {
        String newDirectory = getUserDir() + configProp.getConfigValue("imageDirectory");
        createDirectory(newDirectory);
        return newDirectory;
    }

    public String getUserDir() {
        return System.getProperty("user.dir");
    }

    private void createDirectory(final String newDirectory) {
        File newDirectoryDir = new File(newDirectory);
        if (!newDirectoryDir.exists()) {
            try {
                newDirectoryDir.mkdirs();
            } catch (SecurityException e) {
                throw new DirectoryCanNotBeCreatedException();
            }
        }
    }

    @Transactional
    public void deleteById(final Long id) {
        imageRepository.deleteById(id);
    }

    public Image getImage(final Long imageId) {
        System.out.println("jsem v getImage - imageId = " + imageId);
        return imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException(
                Image.class.getSimpleName(), imageId));
    }

    public void setImageToHint(final HintFormDTO formObject, final Hint hint) {
        if (isFilePresentInHintForm(formObject)) {
            Image newImage = new Image(getFilePath(formObject.getImage()));
            hint.setImage(newImage);
        }
    }
}
