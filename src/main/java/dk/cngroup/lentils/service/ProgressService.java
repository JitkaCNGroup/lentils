package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Progress;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.TemporalAmount;
import java.util.List;

@Service
public class ProgressService {

    ProgressRepository progressRepository;

    @Autowired
    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public ProgressService() {
    }

    public void markCypherSolved(Progress progress) {
        progress.setCypherStatus(CypherStatus.SOLVED);
        progressRepository.save(progress);
    }

    public void markCypherSkipped(Progress progress) {
        progress.setCypherStatus(CypherStatus.SKIPPED);
        progressRepository.save(progress);
    }

    public List<Progress> viewTeamsProgress() {
        return progressRepository.findAll();
    }

    public int getScore(Team team) {
        int score = 0;
        List<Progress> progressList = progressRepository.findByTeam(team);
        for (Progress progress : progressList) {
            score += progress.getCypherStatus().getStatusValue();
        }
        return score;
    }
}
