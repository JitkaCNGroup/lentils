package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void markCypherSolvedForTeam(Long cypherId, Long teamId) {
        Status status = statusRepository.findByTeamIdAndCypherId(teamId, cypherId);
        saveNewStatus(status, CypherStatus.SOLVED);
    }

    public void markCypherSkippedForTeam(Long cypherId, Long teamId) {
        Status status = statusRepository.findByTeamIdAndCypherId(teamId, cypherId);

        saveNewStatus(status, CypherStatus.SKIPPED);
    }

    public List<Status> viewTeamsProgress() {
        return statusRepository.findAll();
    }

    public int getScore(Team team) {

        List<Status> statusList = statusRepository.findByTeam(team);

        return statusList.stream().mapToInt(progress -> progress.getCypherStatus().getStatusValue()).sum();
    }

    private void saveNewStatus(Status status, CypherStatus newStatus) {
        status.setCypherStatus(newStatus);
        statusRepository.save(status);
    }
}
