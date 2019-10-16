package dk.cngroup.lentils.util;

import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.exception.DirectoryCanNotBeCreatedException;
import dk.cngroup.lentils.exception.FileNotFoundException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public final class FileTreatingUtils {

    private FileTreatingUtils() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static MultipartFile getFile(final String stringPath) {
        final File file = new File(stringPath);
        DiskFileItem fileItem = new DiskFileItem("file", "image",
                true, file.getName(), (int) file.length(), file.getParentFile());
        try {
            fileItem.getOutputStream();
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        return new CommonsMultipartFile(fileItem);
    }

    public static DiskFileItem getFileItem(final String stringPath) {
        final File file = new File(stringPath);
        DiskFileItem fileItem = new DiskFileItem("file", "image",
                true, file.getName(), (int) file.length(), file.getParentFile());
        try {
            fileItem.getOutputStream();
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        return fileItem;
    }

    public static String extractFileName(final String filenameWithPath) {
        final File file = new File(filenameWithPath);
        return file.getName();
    }

    public static String getFileNameFromEntity(final Hint hint) {
        return isFilePresentInHintEntity(hint) ? FileTreatingUtils.extractFileName(hint.getImage().getImageUrl()) : "";
    }

    public static boolean isFilePresentInHintForm(final HintFormDTO formObject) {
        MultipartFile file = formObject.getImageFile();
        return !file.isEmpty();
    }

    private static boolean isFilePresentInHintEntity(final Hint hint) {
        Optional<Image> image = Optional.ofNullable(hint.getImage());
        if (image.isPresent()) {
            if (image.get().isFromFile()) {
                return true;
            }
        }
        return false;
    }

    public static String getFileNamefromFormObject(final HintFormDTO formObject) {
        return isFilePresentInHintForm(formObject) ? formObject.getImageFile().getOriginalFilename() : "";
    }

    public static String getFileName(final Hint hint, final HintFormDTO formObject) {
        if (isFilePresentInHintForm(formObject)) {
            return formObject.getImageFile().getOriginalFilename();
        }
        return getFileNameFromEntity(hint);
    }

    public static void setImageToFormObject(final Hint hint, final HintFormDTO formObject) {
        if (isFilePresentInHintEntity(hint)
                && (!isFilePresentInHintForm(formObject))) {
            formObject.setImageFile(getFile(hint.getImage().getImageUrl()));
        }
    }

    public static void createDirectory(final String newDirectory) {
        File newDirectoryDir = new File(newDirectory);
        if (!newDirectoryDir.exists()) {
            try {
                newDirectoryDir.mkdirs();
            } catch (SecurityException e) {
                throw new DirectoryCanNotBeCreatedException();
            }
        }
    }

    public static String getUserDir() {
        return System.getProperty("user.dir");
    }
}
