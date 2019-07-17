package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HintFormConverter {

    private ModelMapper modelMapper;
    private ImageService imageService;

    @Autowired
    public HintFormConverter(final ModelMapper modelMapper,
                          final ImageService imageService) {
        this.modelMapper = modelMapper;
        this.imageService = imageService;
    }

    public HintFormDTO fromEntity(final Hint hint) {
        HintFormDTO formObject = modelMapper.map(hint, HintFormDTO.class);
        Optional<Image> hintImage = Optional.ofNullable(hint.getImage());
        if (hintImage.isPresent()) {
            formObject.setImage(imageService.getFile(hint.getImage().getPath()));
        }
        return formObject;
    }

    public void toEntity(final HintFormDTO formObject, final Hint hint) {
        modelMapper.map(formObject, hint);
        imageService.setImageToHint(formObject, hint);
    }
}
