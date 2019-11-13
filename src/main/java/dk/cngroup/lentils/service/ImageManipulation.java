package dk.cngroup.lentils.service;

import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.Image;

public interface ImageManipulation {

    Image getProperImage(HintFormDTO hintFormDTO, Image actualImage);
}
