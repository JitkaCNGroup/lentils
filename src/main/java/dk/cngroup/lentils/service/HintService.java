package dk.cngroup.lentils.service;

import dk.cngroup.lentils.controller.ImageFileController;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.HintRepository;
import dk.cngroup.lentils.repository.HintTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@Service
public class HintService {
    private final HintRepository hintRepository;
    private final HintTakenRepository hintTakenRepository;
    private final ImageService imageService;

    @Autowired
    public HintService(final HintRepository hintRepository,
                       final HintTakenRepository hintTakenRepository,
                       final ImageService imageService) {
        this.hintRepository = hintRepository;
        this.hintTakenRepository = hintTakenRepository;
        this.imageService = imageService;
    }

    public Hint save(final Hint hint) {
        return hintRepository.save(hint);
    }

    public Hint getHint(final Long hintId) {
        return hintRepository
                .findById(hintId)
                .orElseThrow(() -> new ResourceNotFoundException(Hint.class.getSimpleName(), hintId));
    }

    public List<Hint> getAllNotTakenByTeamAndCypher(final Team team, final Cypher cypher) {
        return hintRepository.findHintsNotTakenByTeam(team.getTeamId(),
                cypher.getCypherId());
    }

    public List<Hint> saveAll(final List<Hint> hints) {
        return hintRepository.saveAll(hints);
    }

    @Transactional
    public void deleteById(final Long id) {
        hintTakenRepository.deleteAllByHintHintId(id);
        hintRepository.deleteById(id);
    }

    public String getImageUri(final Long hintId) {
        String imagePath = getHint(hintId).getImage().getImageUrl();
        String filename = imageService.getPureFileName(imagePath);
        return MvcUriComponentsBuilder
                .fromMethodName(
                        ImageFileController.class,
                        "serveFile",
                        filename)
                .build()
                .normalize()
                .toString();
    }

    public String getFileUrlForHint(final Long hintId) {
        if (getHint(hintId).getImage() == null) {
            return "";
        }
        if (!getHint(hintId).getImage().isLocal()) {
            return getHint(hintId).getImage().getImageUrl();
        }
        return getImageUri(hintId);
    }
}
