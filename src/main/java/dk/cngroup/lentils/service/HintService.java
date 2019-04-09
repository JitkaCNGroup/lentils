package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
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

    public List<Hint> getAllByCypher(final Cypher cypher) {
        return hintRepository.findByCypher(cypher);
    }

    public Hint save(final Hint hint) {
        return hintRepository.save(hint);
    }

    public List<Hint> saveAll(final List<Hint> hints) {
        return hintRepository.saveAll(hints);
    }

    public List<Hint> getAll() {
        return hintRepository.findAll();
    }

    public Hint getHint(final Long hintId) {
        return hintRepository
                .findById(hintId)
                .orElseThrow(() -> new ResourceNotFoundException(Hint.class.getSimpleName(), hintId));
    }

    public void deleteById(final Long id) {
        hintRepository.deleteById(id);
    }

    public void deleteAlHintsByCypher(final Cypher cypher) {
        hintRepository.deleteByCypher(cypher);
    }
}
