package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.service.ImageService;
import dk.cngroup.lentils.util.FileTreatingUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HintMapper extends ModelMapperWrapper {

    private ImageService imageService;

    private final PropertyMap<HintFormDTO, Hint> skipImageFieldMap = new PropertyMap<HintFormDTO, Hint>() {
        protected void configure() {
            skip(destination.getImage());
        }
    };

    @Autowired
    public HintMapper(final ImageService imageService) {
        this.imageService = imageService;
    }

    public Hint map(final HintFormDTO hintFormDTO, final Hint hint) {
        Image actualImage = hint.getImage();
        ModelMapper mapper = super.getModelMapper();
        TypeMap<HintFormDTO, Hint> typeMap = mapper.getTypeMap(HintFormDTO.class, Hint.class);
        if (typeMap == null) {
            mapper.addMappings(skipImageFieldMap);
        }
        mapper.map(hintFormDTO, hint);
        Image image = imageService.generateImageFromDto(
                hintFormDTO.getImageUrl(),
                hintFormDTO.getImageSource(),
                hintFormDTO.getImageFile(),
                actualImage);
        hint.setImage(image);
        if (image == null) {
            imageService.deleteImage(actualImage);
        }
        return hint;
    }

    public HintFormDTO map(final Hint hint) {
        HintFormDTO hintFormDTO = super.getModelMapper().map(hint, HintFormDTO.class);
        hintFormDTO.setFilename(FileTreatingUtils.getFileNameFromEntity(hint));
        hintFormDTO.setImageFile(null);
        hintFormDTO.setImageUrl(imageService.getImageUrlForHintDto(hint));
        hintFormDTO.setImageSource(imageService.getImageSource(hint));

        return hintFormDTO;
    }
}
