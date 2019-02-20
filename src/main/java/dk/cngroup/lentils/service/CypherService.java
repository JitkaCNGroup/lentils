package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.repository.CypherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CypherService {

    CypherRepository cypherRepository;

    HintService hintService;

    @Autowired
    public CypherService(CypherRepository cypherRepository) {
        this.cypherRepository = cypherRepository;
    }

    public Cypher add(Cypher cypher) {
        return cypherRepository.save(cypher);
    }

    public List<Hint> getHintsForStage(Integer stage) {
        Cypher cypher = cypherRepository.findByStage(stage);
        return hintService.getHintsForCypher(cypher);

    }

    public Cypher getNext(Integer stage) {
        Cypher cypher = cypherRepository.findByStage(stage);
        return cypherRepository.findByStage(cypher.getStage() + 1);
    }

    public boolean checkCodeword(String codeword, Integer stage) {
        Cypher cypher = cypherRepository.findByStage(stage);
        return codeword.equals(cypher.getCodeword());
    }

    public void deleteAll() {
        cypherRepository.deleteAll();
    }

    public void saveAll(List<Cypher> cyphers) {
        cypherRepository.saveAll(cyphers);
    }

    public List<Cypher> getAll() {
        return cypherRepository.findAll();
    }
}
