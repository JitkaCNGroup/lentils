package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.HintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HintService {
    private HintRepository hintRepository;
    private HintTakenService hintTakenService;

    @Autowired
    public HintService(final HintRepository hintRepository,
                       final HintTakenService hintTakenService) {
        this.hintRepository = hintRepository;
        this.hintTakenService = hintTakenService;
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
        List<Hint> hintsNotTakenByTeam = hintRepository.findAllNotTaken(team.getTeamId());
        return hintsNotTakenByTeam
                .stream()
                .filter(hint -> hint.getCypherId().equals(cypher.getCypherId()))
                .collect(Collectors.toList());
    }

    public List<Hint> saveAll(final List<Hint> hints) {
        return hintRepository.saveAll(hints);
    }

    public void deleteById(final Long id) {
        hintRepository.deleteById(id);
    }
}
