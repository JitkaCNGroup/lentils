package dk.cngroup.lentils.util;

import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.exception.DirectoryCanNotBeCreatedException;
import dk.cngroup.lentils.exception.FileNotDeletedException;
import dk.cngroup.lentils.exception.FileNotFoundException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
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

    public static String extractFileName(final String filenameWithPath) {
        final File file = new File(filenameWithPath);
        return file.getName();
    }

    public static String getFileNameFromEntity(final Hint hint) {
        return isFilePresentInHintEntity(hint) ? FileTreatingUtils.extractFileName(hint.getImage().getImageUrl()) : "";
    }

    private static boolean isFilePresentInHintEntity(final Hint hint) {
        Optional<Image> image = Optional.ofNullable(hint.getImage());
        if (!image.isPresent()) {
            return false;
        }
        return  image.get().isLocal();
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

    public static void deleteImageFile(final Image image) {
        if (fileExists(image)) {
            try {
                FileUtils.forceDelete(FileUtils.getFile(FileTreatingUtils.getUserDir() + image.getImageUrl()));
            } catch (IOException e) {
                throw new FileNotDeletedException();
            }
        }
    }

    private static boolean fileExists(final Image image) {
        return image != null && image.isLocal() && image.getImageUrl() != "";
    }
}
