package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.HintTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HintTakenService {

    private HintTakenRepository hintTakenRepository;

    @Autowired
    public HintTakenService(final HintTakenRepository hintTakenRepository) {
        this.hintTakenRepository = hintTakenRepository;
    }

    public void takeHint(final Team team, final Hint hint) {
        hintTakenRepository.save(new HintTaken(team, hint));
    }

    public void revertHint(final Team team, final Hint hint) {
        HintTaken hintTaken = hintTakenRepository.findByTeamAndHint(team, hint);
        hintTakenRepository.delete(hintTaken);
    }

    public int getHintScore(final Team team, final Cypher cypher) {
        int hintScore = 0;
        final List<Hint> hints = cypher.getHints();
        if (hints != null) {
            for (Hint hint : hints) {
                HintTaken hintTaken = hintTakenRepository.findByTeamAndHint(team, hint);
                if (hintTaken != null) {
                    hintScore += hint.getValue();
                }
            }
        }
        return hintScore;
    }

    public List<HintTaken> getTakenHintsOfTeam(final Team team) {
        return hintTakenRepository.findByTeam(team);
    }

    public List<HintTaken> getAllByTeam(final Team team) {
        return  hintTakenRepository.findByTeam(team);
    }

    public List<HintTaken> getAllByTeamAndCypher(final Team team, final Cypher cypher) {
    List<HintTaken> hintsTakenByTeamAndCypher = hintTakenRepository.findAllByTeamAndCypher(team.getTeamId(),
            cypher.getCypherId());
        return hintsTakenByTeamAndCypher;
    }

    public List<HintTaken> getAll() {
        return hintTakenRepository.findAll();
    }
}
