package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Image;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.convertors.HintMapper;
import dk.cngroup.lentils.service.ImageService;
import dk.cngroup.lentils.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/hint")
public class HintController {
    private static final String VIEW_ADMIN_HINT_LIST = "hint/list";
    private static final String VIEW_ADMIN_HINT_DETAIL = "hint/detail";
    private static final String REDIRECT_ADMIN_HINT_LIST = "redirect:/admin/hint";
    private static final String CYPHERID_PARAMETER = "?cypherId=";
    private static final String HEADING_NEW_HINT = "Nový hint";
    private static final String HEADING_EDIT_HINT = "Upravit hint";
    private static final String TEMPLATE_ATTR_CYPHER = "cypher";
    private static final String TEMPLATE_ATTR_HEADING = "heading";
    private static final String TEMPLATE_ATTR_FILENAME = "filename";
    private static final String TEMPLATE_ATTR_FILE = "file";
    private static final String TEMPLATE_ATTR_HINT = "hint";

    private final CypherService cypherService;
    private final HintService hintService;
    private final HintMapper mapper;
    private ImageService imageService;

    @Autowired
    public HintController(final CypherService cypherService,
                          final HintService hintService,
                          final HintMapper hintMapper,
                          final ImageService imageService) {
        this.cypherService = cypherService;
        this.hintService = hintService;
        this.mapper = hintMapper;
        this.imageService = imageService;
    }

    @GetMapping(value = "")
    public String hints(@RequestParam("cypherId") final Long cypherId, final Model model) {
        Cypher cypher = cypherService.getCypher(cypherId);
        model.addAttribute(TEMPLATE_ATTR_CYPHER, cypher);
        return VIEW_ADMIN_HINT_LIST;
    }

    @GetMapping(value = "/delete/{hintId}")
    public String deleteHint(@PathVariable("hintId") final Long hintId) {
        Hint hint = hintService.getHint(hintId);
        Long cypherId = hint.getCypherId();
        hintService.deleteById(hintId);
        return REDIRECT_ADMIN_HINT_LIST + CYPHERID_PARAMETER + cypherId;
    }

    @GetMapping(value = "/update/{hintId}")
    public String updateHintForm(@PathVariable("hintId") final Long hintId, final Model model) {
        Hint hint = hintService.getHint(hintId);
        HintFormDTO hintFormDto = mapper.map(hint, HintFormDTO.class);
        model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_EDIT_HINT);
        model.addAttribute(TEMPLATE_ATTR_HINT, hintFormDto);
        model.addAttribute(TEMPLATE_ATTR_FILENAME, FileUtils.getFileNameFromEntity(hint));
        addImageUri(model, hintFormDto);
        return VIEW_ADMIN_HINT_DETAIL;
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(final @PathVariable String filename) {
        Resource file = imageService.loadAsResource(imageService.getFilePathToShow(filename));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(value = "/update/{hintId}")
    public String updateHint(@PathVariable("hintId") final Long hintId,
                       @Valid @ModelAttribute(TEMPLATE_ATTR_HINT) final HintFormDTO hintFormDto,
                       final BindingResult bindingResult,
                       final Model model) {
        Hint hint = hintService.getHint(hintId);
        FileUtils.setImageToFormObject(hint, hintFormDto);
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_EDIT_HINT);
            model.addAttribute(TEMPLATE_ATTR_HINT, hintFormDto);
            model.addAttribute(TEMPLATE_ATTR_FILENAME, FileUtils.getFileName(hint, hintFormDto));
            return VIEW_ADMIN_HINT_DETAIL;
        }
        imageService.saveImageFile(hintFormDto);
        Image image = hint.getImage();
        mapper.map(hintFormDto, hint);
        if (!FileUtils.isFilePresentInHintForm(hintFormDto)) {
            hint.setImage(image);
        }
        hintService.save(hint);
        return REDIRECT_ADMIN_HINT_LIST + CYPHERID_PARAMETER + hint.getCypherId();
    }

    @GetMapping(value = "/add")
    public String newHint(@RequestParam("cypherId") final Long cypherId, final Model model) {
        Hint hint = cypherService.addHint(cypherId);
        HintFormDTO hintFormDto = new HintFormDTO();
        hintFormDto.setCypher(cypherService.getCypher(cypherId));
        model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_NEW_HINT);
        model.addAttribute(TEMPLATE_ATTR_HINT, hintFormDto);
        model.addAttribute(TEMPLATE_ATTR_FILENAME, "");
        return VIEW_ADMIN_HINT_DETAIL;
    }

    @PostMapping(value = "/add")
    public String saveNewHint(@Valid @ModelAttribute(TEMPLATE_ATTR_HINT) final HintFormDTO hintFormDto,
                       @RequestParam("cypherId") final Long cypherId,
                       final BindingResult bindingResult,
                       final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_NEW_HINT);
            model.addAttribute(TEMPLATE_ATTR_HINT, hintFormDto);
            model.addAttribute(TEMPLATE_ATTR_FILENAME, FileUtils.getFileNamefromFormObject(hintFormDto));
            return VIEW_ADMIN_HINT_DETAIL;
        }
        Hint hint = new Hint(cypherService.getCypher(cypherId));
        imageService.saveImageFile(hintFormDto);
        mapper.map(hintFormDto, hint);
        hintService.save(hint);
        return REDIRECT_ADMIN_HINT_LIST + CYPHERID_PARAMETER + hint.getCypherId();
    }

    private void addImageUri(final Model model, final HintFormDTO hintFormDto) {
        if (hintFormDto.getImage() == null) {
            model.addAttribute(TEMPLATE_ATTR_FILE, "");
        } else {
            model.addAttribute(TEMPLATE_ATTR_FILE, MvcUriComponentsBuilder.fromMethodName(HintController.class,
                    "serveFile", hintFormDto.getImage().getOriginalFilename()).build().toString());
        }
    }
}
