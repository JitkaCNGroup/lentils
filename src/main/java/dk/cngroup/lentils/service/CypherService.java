package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.CypherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CypherService {
    private CypherRepository cypherRepository;
    private TeamService teamService;
    private StatusService statusService;
    private HintTakenService hintTakenService;

    @Autowired
    public CypherService(CypherRepository cypherRepository
            , TeamService teamService
            , StatusService statusService
            , HintTakenService hintTakenService) {
        this.cypherRepository = cypherRepository;
        this.teamService = teamService;
        this.statusService = statusService;
        this.hintTakenService = hintTakenService;
    }

    public Cypher save(Cypher cypher) {
        return cypherRepository.save(cypher);
    }

    public List<Hint> getHintsForStage(Integer stage) {
        Cypher cypher = cypherRepository.findByStage(stage);
        return new ArrayList<>(cypher.getHintsSet());
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
        Optional<Cypher> cypher = cypherRepository.findById(cypherId);
        return getScore(cypher.get(), team.get());
    }

    public Cypher findById(Long cypherId) {
        return cypherRepository.findById(cypherId).get();
    }
}
