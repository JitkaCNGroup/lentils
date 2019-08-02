package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.service.ImageService;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class HintMapper extends ModelMapperWrapper {

    private ImageService imageService;

    private final Converter<MultipartFile, Image> multipartFileToImageConverter =
            ctx -> ctx.getSource() == null ? null : imageService.getImageFromMultipartFile(ctx.getSource());

    private final Converter<Image, MultipartFile> imageToMultipartFileConverter =
            ctx -> ctx.getSource() == null ? null : imageService.getFile(ctx.getSource().getPath());

    @Autowired
    public HintMapper(final ImageService imageService) {
        this.imageService = imageService;

        super.getModelMapper().typeMap(HintFormDTO.class, Hint.class)
                .addMappings(mapper -> mapper.using(multipartFileToImageConverter)
                        .map(HintFormDTO::getImage, Hint::setImage));

        super.getModelMapper().typeMap(Hint.class, HintFormDTO.class)
                .addMappings(mapper -> mapper.using(imageToMultipartFileConverter)
                        .map(Hint::getImage, HintFormDTO::setImage));
    }
}
