package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.HintTakenKey;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.HintTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HintTakenService {

    HintTakenRepository hintTakenRepository;

    @Autowired
    public HintTakenService(HintTakenRepository hintTakenRepository) {
        this.hintTakenRepository = hintTakenRepository;
    }

    public void writeHintTaken(Team team, Hint hint) {
        HintTakenKey hintTakenKey = new HintTakenKey(team.getId(), hint.getHintId());
        hintTakenRepository.save(new HintTaken(hintTakenKey));
    }

    public List<HintTaken> getHintTakenForTeam(Long teamId) {
        return hintTakenRepository.findByTeamId(teamId);
    }
}
