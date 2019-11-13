package dk.cngroup.lentils.service;

import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.util.FileTreatingUtils;
import org.jetbrains.annotations.NotNull;

public class ImageManipulationRemoteImpl implements ImageManipulation {

    @Override
    public Image getProperImage(final HintFormDTO hintFormDTO, final Image actualImage) {
        if (actualImage == null) {
            return new Image(hintFormDTO.getImageUrl(), false);
        }
        return setNewRemoteImage(hintFormDTO.getImageUrl(), actualImage);
    }

    @NotNull
    private Image setNewRemoteImage(final String imageUrl, final Image actualImage) {
        FileTreatingUtils.deleteImageFile(actualImage);
        actualImage.setImageUrl(imageUrl);
        actualImage.setLocal(false);
        return actualImage;
    }
}
