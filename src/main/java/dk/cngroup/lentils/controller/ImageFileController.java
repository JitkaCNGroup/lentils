package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.service.ImageManipulationLocalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageFileController {

    private ImageManipulationLocalImpl imageManipulation;

    @Autowired
    public ImageFileController(final ImageManipulationLocalImpl imageManipulation) {
        this.imageManipulation = imageManipulation;
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(final @PathVariable String filename) {
        Resource file = imageManipulation.loadAsResource(imageManipulation.getFilePathToShow(filename));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
