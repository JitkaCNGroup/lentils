package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.HintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HintService {
    private final String HINT_ID = "Hint with ID ";
    private HintRepository hintRepository;

    @Autowired
    public HintService(HintRepository hintRepository) {
        this.hintRepository = hintRepository;
    }

    public List<Hint> getAllByCypher(Cypher cypher) {
        return hintRepository.findByCypher(cypher);
    }

    public Hint save(Hint hint) {
        return hintRepository.save(hint);
    }

    public List<Hint> saveAll(List<Hint> hints) {
        return hintRepository.saveAll(hints);
    }

    public List<Hint> getAll() {
        return hintRepository.findAll();
    }

    public Hint getHint(Long hintId) {
        Optional<Hint> hint = hintRepository.findById(hintId);
        if (hint.isPresent()){
            return hint.get();
        }
        throw new ResourceNotFoundException(HINT_ID + hintId);
    }

    public void deleteById(Long id) {
        hintRepository.deleteById(id);
    }

    public void deleteAlHintsByCypher(Cypher cypher) {
        hintRepository.deleteByCypher(cypher);
    }
}
