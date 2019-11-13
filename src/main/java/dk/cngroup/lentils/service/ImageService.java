package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.ImageSource;
import dk.cngroup.lentils.repository.ImageRepository;
import dk.cngroup.lentils.util.FileTreatingUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image save(final Image image) {
        return imageRepository.save(image);
    }

    public void deleteImage(final Image image) {
        if (image == null) {
            return;
        }
        imageRepository.deleteById(image.getImageId());
        FileTreatingUtils.deleteImageFile(image);
    }

    public String getImageUrlForHintDto(final Hint hint) {
        return !imageUrlExists(hint) ? "" : hint.getImage().getImageUrl();
    }

    private boolean imageUrlExists(final Hint hint) {
        return hint.getImage() != null && !hint.getImage().isLocal();
    }

    public ImageSource getImageSource(final Hint hint) {
       if (!Optional.ofNullable(hint.getImage()).isPresent()) {
           return ImageSource.NONE;
       }
       if (hint.getImage().isLocal()) {
           return ImageSource.LOCAL;
       }
       return ImageSource.REMOTE;
    }

    public Image generateImageFromDto(final HintFormDTO hintFormDTO,
                                      final Image actualImage) {
        ImageManipulation imageManipulation;
        if (hintFormDTO.getImageSource().equals(ImageSource.NONE)) {
            return null;
        }
        if (hintFormDTO.getImageSource().equals(ImageSource.REMOTE)) {
            imageManipulation = new ImageManipulationRemoteImpl();
        } else {
            imageManipulation = new ImageManipulationLocalImpl();
        }
        return imageManipulation.getProperImage(hintFormDTO, actualImage);
    }
}