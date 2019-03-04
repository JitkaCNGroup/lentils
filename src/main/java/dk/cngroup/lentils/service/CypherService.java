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
    private final String CYPHER_ID = "Cypher with ID ";
    private CypherRepository cypherRepository;
    private TeamService teamService;
    private StatusService statusService;
    private HintTakenService hintTakenService;
    private HintService hintService;

    @Autowired
    public CypherService(CypherRepository cypherRepository
            , TeamService teamService
            , StatusService statusService
            , HintTakenService hintTakenService
    , HintService hintService) {
        this.cypherRepository = cypherRepository;
        this.teamService = teamService;
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
        this.hintService = hintService;
    }

    public Cypher save(Cypher cypher) {
        return cypherRepository.save(cypher);
    }

    public List<Hint> getHintsForStage(Integer stage) {
        Cypher cypher = cypherRepository.findByStage(stage);
        return cypher.getHints();
    }

    public Cypher getNext(Integer stage) {
        Cypher cypher = cypherRepository.findByStage(stage);
        return cypherRepository.findByStage(cypher.getStage() + 1);
    }

    public boolean checkCodeword(String codeword, Integer stage) {
        Cypher cypher = cypherRepository.findByStage(stage);
        return codeword.equals(cypher.getCodeword());
    }
    public Cypher getByStage(int stage){
        return cypherRepository.findByStage(stage);
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

    public int getScore(Cypher cypher, Team team) {
        int statusScore = statusService.getStatusScore(team, cypher );
        int hintScore = hintTakenService.getHintScore(team, cypher);
        return statusScore - hintScore;
    }

    public int getScore(Long cypherId, Long teamId) {
        Optional<Team> team = teamService.get(teamId);
        Cypher cypher = getCypher(cypherId);
        return getScore(cypher, team.get());
    }

    public void deleteById(Long id) {
        cypherRepository.deleteById(id);
    }



    public Cypher getCypher(Long cypherId) {
        Optional<Cypher> cypher = cypherRepository.findById(cypherId);
        if (cypher.isPresent()){
            return cypher.get();
        }
        throw new ResourceNotFoundException(CYPHER_ID + cypherId);
    }

    public Hint addHint(Long cypherId) {
        Cypher cypher = getCypher(cypherId);
        Hint hint = new Hint();
        hint.setCypher(cypher);
        return hint;
    }
}
