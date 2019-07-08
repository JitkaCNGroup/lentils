package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.HintRepository;
import dk.cngroup.lentils.repository.HintTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HintService {
    private HintRepository hintRepository;
    private HintTakenRepository hintTakenRepository;

    @Autowired
    public HintService(final HintRepository hintRepository,
                       final HintTakenRepository hintTakenRepository) {
        this.hintRepository = hintRepository;
        this.hintTakenRepository = hintTakenRepository;
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
}
