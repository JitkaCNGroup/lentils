package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Progress;
import dk.cngroup.lentils.entity.ProgressKey;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {

    private ProgressRepository progressRepository;

    @Autowired
    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public void markCypherSolvedForTeam(Long cypherId, Long teamId) {
        Progress progress = progressRepository.findByTeamIdAndCypherId(teamId, cypherId);
                saveNewStatus(progress, CypherStatus.SOLVED);
     }

    public void markCypherSkippedForTeam(Long cypherId, Long teamId) {
        Progress progress = progressRepository.findByTeamIdAndCypherId(teamId, cypherId);

        saveNewStatus(progress, CypherStatus.SKIPPED);
    }


    public List<Progress> viewTeamsProgress() {
        return progressRepository.findAll();
    }

    public int getScore(Team team) {

        List<Progress> progressList = progressRepository.findByTeam(team);
        /**
         * Pochopila, muZu pouzit - https://blog.frantovo.cz/c/339/Java%208%3A%20Stream%20API
         * jo, smazu
         */
        return progressList.stream().mapToInt(progress -> progress.getCypherStatus().getStatusValue()).sum();

    }

    private void saveNewStatus(Progress progress, CypherStatus newStatus) {
        progress.setCypherStatus(newStatus);
        progressRepository.save(progress);
    }
}
