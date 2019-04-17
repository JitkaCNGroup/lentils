package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.HintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HintService {
    private HintRepository hintRepository;

    @Autowired
    public HintService(final HintRepository hintRepository) {
        this.hintRepository = hintRepository;
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
        List<Hint> hintsNotTakenByTeamOnCypher = hintRepository.findHintsNotTakenByTeam(team.getTeamId(),
                cypher.getCypherId());
        return hintsNotTakenByTeamOnCypher;
    }

    public List<Hint> saveAll(final List<Hint> hints) {
        return hintRepository.saveAll(hints);
    }

    public void deleteById(final Long id) {
        hintRepository.deleteById(id);
    }
}
