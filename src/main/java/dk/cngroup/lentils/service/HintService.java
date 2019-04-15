package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.HintRepository;
import dk.cngroup.lentils.repository.HintTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HintService {
    private HintTakenRepository hintTakenRepository;
    private HintRepository hintRepository;

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
        List<Hint> cypherHints = hintRepository.findByCypher(cypher);
        List<HintTaken> allHintsTakenByTeam = hintTakenRepository.findByTeam(team);
        List<Hint> takenHintsByTeam = allHintsTakenByTeam.stream()
                .map(hintTaken -> hintTaken.getHint())
                .collect(Collectors.toList());
        cypherHints.removeAll(takenHintsByTeam);
        return cypherHints;
    }

    public List<Hint> saveAll(final List<Hint> hints) {
        return hintRepository.saveAll(hints);
    }

    public void deleteById(final Long id) {
        hintRepository.deleteById(id);
    }
}
