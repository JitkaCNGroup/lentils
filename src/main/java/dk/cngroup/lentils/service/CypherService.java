package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.CypherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CypherService {
    private final CypherRepository cypherRepository;
    private final TeamService teamService;
    private final StatusService statusService;
    private final HintTakenService hintTakenService;

    @Autowired
    public CypherService(final CypherRepository cypherRepository,
                         final TeamService teamService,
                         final StatusService statusService,
                         final HintTakenService hintTakenService) {
        this.cypherRepository = cypherRepository;
        this.teamService = teamService;
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
    }

    public Cypher save(final Cypher cypher) {
        return cypherRepository.save(cypher);
    }

    public List<Hint> getHintsForStage(final Integer stage) {
        Cypher cypher = cypherRepository.findByStage(stage);
        return cypher.getHints();
    }

    public Cypher getNext(final Integer stage) {
        return cypherRepository.findFirstByStageGreaterThan(stage);
    }

    public boolean checkCodeword(final String codeword, final Integer stage) {
        Cypher cypher = cypherRepository.findByStage(stage);
        return codeword.equals(cypher.getCodeword());
    }

    public Cypher getByStage(final int stage) {
        return cypherRepository.findByStage(stage);
    }

    public void deleteAll() {
        cypherRepository.deleteAll();
    }

    public void saveAll(final List<Cypher> cyphers) {
        cypherRepository.saveAll(cyphers);
    }

    public List<Cypher> getAll() {
        return cypherRepository.findAll();
    }

    public int getScore(final Cypher cypher, final Team team) {
        int statusScore = statusService.getStatusScore(team, cypher);
        int hintScore = hintTakenService.getHintScore(team, cypher);
        return statusScore - hintScore;
    }

    public int getScore(final Long cypherId, final Long teamId) {
        Team team = teamService.getTeam(teamId);
        Cypher cypher = getCypher(cypherId);
        return getScore(cypher, team);
    }

    public void deleteById(final Long id) {
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
