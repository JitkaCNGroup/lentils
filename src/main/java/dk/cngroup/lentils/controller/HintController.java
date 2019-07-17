package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.dto.HintFormDTO;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.convertors.ModelMapperWrapper;
import dk.cngroup.lentils.service.ImageService;
import dk.cngroup.lentils.service.convertors.HintFormConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private static final String TEMPLATE_ATTR_FORM_OBJECT = "formObject";
    private static final String TEMPLATE_ATTR_HEADING = "heading";
    private static final String TEMPLATE_ATTR_FILENAME = "filename";
    private static final String TEMPLATE_ATTR_FILEID = "fileId";

    private final CypherService cypherService;
    private final HintService hintService;
    private final ModelMapperWrapper mapper;
    private HintFormConverter hintFormConverter;
    private ImageService imageService;

    @Autowired
    public HintController(final CypherService cypherService,
                          final HintService hintService,
                          final ModelMapperWrapper modelMapperWrapper
						  final HintFormConverter hintFormConverter,
                          final ImageService imageService) {
        this.cypherService = cypherService;
        this.hintService = hintService;
		this.mapper = modelMapperWrapper;
        this.hintFormConverter = hintFormConverter;
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
        HintFormDTO hintFormDto = mapper.map(hintService.getHint(hintId), HintFormDTO.class);
        model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_EDIT_HINT);
        model.addAttribute(TEMPLATE_ATTR_HINT, hintFormDto);

        Hint hint = hintService.getHint(hintId);
        final HintFormDTO formObject = hintFormConverter.fromEntity(hint);
        String filename = imageService.getFileNameFromEntity(hint);
        Long imageId = imageService.getFileIdFromEntity(hint);
        model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_EDIT_HINT);
        model.addAttribute(TEMPLATE_ATTR_FORM_OBJECT, formObject);
        model.addAttribute(TEMPLATE_ATTR_FILENAME, filename);
        model.addAttribute(TEMPLATE_ATTR_FILEID, imageId);
        System.out.println("cesta k souboru - filename: " + filename);
        System.out.println("cesta k souboru - formObject: " + formObject.getImage().getOriginalFilename());
        System.out.println("file Id: " + imageId);
       return VIEW_ADMIN_HINT_DETAIL;
    }

    @PostMapping(value = "/update/{hintId}")
    public String updateHint(@PathVariable("hintId") final Long hintId,
	//				   @Valid @ModelAttribute(TEMPLATE_ATTR_HINT) final HintFormDTO hintFormDto,
                       @Valid @ModelAttribute final HintFormDTO formObject,
                       final BindingResult bindingResult,
                       final Model model) {
        Hint hint = hintService.getHint(hintId);
        imageService.setImageToFormObject(hint, formObject);
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_EDIT_HINT);
            model.addAttribute(TEMPLATE_ATTR_FORM_OBJECT, formObject);
	//		model.addAttribute(TEMPLATE_ATTR_HINT, hintFormDto);
            model.addAttribute(TEMPLATE_ATTR_FILENAME, imageService.getFileName(hint, formObject));
            return VIEW_ADMIN_HINT_DETAIL;
        }
        imageService.saveImageFile(formObject);
        hintFormConverter.toEntity(formObject, hint);
        Hint hint1 = hintService.save(hint);
	//	Hint hint = hintService.getHint(hintId);
	//	mapper.map(hintFormDto, hint);
    //  hintService.save(hint);
        return REDIRECT_ADMIN_HINT_LIST + "?cypherId=" + hint1.getCypherId();
	//	return REDIRECT_ADMIN_HINT_LIST + CYPHERID_PARAMETER + hint.getCypherId();
    }

    @GetMapping(value = "/add")
    public String newHint(@RequestParam("cypherId") final Long cypherId, final Model model) {
        Hint hint = cypherService.addHint(cypherId);
        final HintFormObject formObject = hintFormConverter.fromEntity(hint);
	//	HintFormDTO hintFormDto = mapper.map(cypherService.addHint(cypherId), HintFormDTO.class);
        model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_NEW_HINT);
        model.addAttribute(TEMPLATE_ATTR_FORM_OBJECT, formObject);
	//	model.addAttribute(TEMPLATE_ATTR_HINT, new hintFormDto);
        model.addAttribute(TEMPLATE_ATTR_FILENAME, "");
        return VIEW_ADMIN_HINT_DETAIL;
    }

    @PostMapping(value = "/add")
    //public String saveNewHint(@Valid @ModelAttribute(TEMPLATE_ATTR_HINT) final HintFormDTO hintFormDto,
	//					@RequestParam("cypherId") final Long cypherId,
    public String saveNewHint(@Valid @ModelAttribute final HintFormDTO formObject,
                       final BindingResult bindingResult,
                       final Model model) {
        final Hint hint = new Hint();
        if (bindingResult.hasErrors()) {
            model.addAttribute(TEMPLATE_ATTR_HEADING, HEADING_NEW_HINT);
            model.addAttribute(TEMPLATE_ATTR_FORM_OBJECT, formObject);
		//	model.addAttribute(TEMPLATE_ATTR_HINT, hintFormDto);
            model.addAttribute(TEMPLATE_ATTR_FILENAME, imageService.getFileNamefromFormObject(formObject));
            return VIEW_ADMIN_HINT_DETAIL;
        }
        imageService.saveImageFile(formObject);
        hintFormConverter.toEntity(formObject, hint);
        Hint hint1 = hintService.save(hint);
        return REDIRECT_ADMIN_HINT_LIST + "?cypherId=" + hint1.getCypherId();
	//	Hint hint = new Hint(cypherService.getCypher(cypherId));
    //    mapper.map(hintFormDto, hint);
    //    hintService.save(hint);
    //    return REDIRECT_ADMIN_HINT_LIST + CYPHERID_PARAMETER + hint.getCypherId();
    }
}
