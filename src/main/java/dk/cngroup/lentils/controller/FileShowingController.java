package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.service.ImageService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequestMapping("/image")
public class FileShowingController {
    private static final String TEMPLATE_ATTR_FILE = "file";

    private ImageService imageService;

    @Autowired
    public FileShowingController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/{imageId}")
    public DiskFileItem image(@PathVariable("imageId") final Long imageId) {
        System.out.println("imageId: " + imageId);
//        System.out.println("image: " + imageService.getImage(Long.parseLong(imageId)));
//        System.out.println("path: " + imageService.getImage(Long.parseLong(imageId)).getPath());
        return imageService.getFileItem(imageService.getImage(imageId).getPath());
//        return imageService.loadAsResource(imageService.getUserDir() + imageService.getImage(imageId).getPath());
    }
}
