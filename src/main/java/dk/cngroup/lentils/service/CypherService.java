package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.util.CzechCharsetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CypherService {
    private final CypherRepository cypherRepository;
    private StatusService statusService;
    private HintService hintService;

    @Autowired
    public void setStatusService(final StatusService statusService) {
        this.statusService = statusService;
    }

    @Autowired
    public void setHintService(final HintService hintService) {
        this.hintService = hintService;
    }

    @Autowired
    public CypherService(final CypherRepository cypherRepository) {
        this.cypherRepository = cypherRepository;
    }

    public Cypher save(final Cypher cypher) {
        return cypherRepository.save(cypher);
    }

    public Cypher getNext(final Integer stage) {
        return cypherRepository.findFirstByStageGreaterThanOrderByStageAsc(stage);
    }

    public boolean checkCodeword(final Cypher cypher, final String codeword) {
        return compareCodewords(codeword, cypher.getCodeword());
    }

    public boolean checkTrapCodeword(final Cypher cypher, final String codeword) {
        if (cypher.getTrapCodeword() != null && cypher.getTrapCodeword().isEmpty()) {
            return false;
        }

        return compareCodewords(codeword, cypher.getTrapCodeword());
    }

    private boolean compareCodewords(final String codeword1, final String codeword2) {
        String normalizedCodeword1 = CzechCharsetUtils.replaceCzechSpecialCharacters(codeword1);
        String normalizedCodeword2 = CzechCharsetUtils.replaceCzechSpecialCharacters(codeword2);

        return normalizedCodeword1.equalsIgnoreCase(normalizedCodeword2);
    }

    public Cypher getByStage(final int stage) {
        return cypherRepository.findByStage(stage);
    }

    public void saveAll(final List<Cypher> cyphers) {
        cypherRepository.saveAll(cyphers);
    }

    public List<Cypher> getAll() {
        return cypherRepository.findAll();
    }

    public List<Cypher> getAllCyphersOrderByStageAsc() {
        return cypherRepository.findAllByOrderByStageAsc();
    }

    public Cypher getFirstOrderByStageAsc() {
        return cypherRepository.findFirstByOrderByStageAsc();
    }

    @Transactional
    public void deleteById(final Long id) {
        getCypher(id).getHints().stream()
                .forEach(hint -> hintService.deleteById(hint.getHintId()));
        statusService.deleteAllByCypherId(id);
        cypherRepository.deleteById(id);
    }

    public Cypher getCypher(final Long cypherId) {
        Optional<Cypher> cypher = cypherRepository.findById(cypherId);
        if (cypher.isPresent()) {
            return cypher.get();
        }
        throw new ResourceNotFoundException(Cypher.class.getSimpleName(), cypherId);
    }

    public Hint addHint(final Long cypherId) {
        Cypher cypher = getCypher(cypherId);
        Hint hint = new Hint();
        hint.setCypher(cypher);
        return hint;
    }
}
