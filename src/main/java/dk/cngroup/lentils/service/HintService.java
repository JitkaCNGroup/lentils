package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.repository.HintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HintService {

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

    public void deleteById(Long id) {
        hintRepository.deleteById(id);
    }

    public void deleteAlHintsByCypher(Cypher cypher) {
        hintRepository.deleteByCypher(cypher);
    }
}
