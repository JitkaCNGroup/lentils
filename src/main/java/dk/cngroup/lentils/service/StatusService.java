package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
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
    public StatusService(final StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void markCypher(final Cypher cypher, final Team team, final CypherStatus cypherStatus) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        saveNewStatus(status, cypherStatus);
    }

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public int getStatusScore(final Team team, final Cypher cypher) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        return status.getCypherStatus().getStatusValue();
    }

    public String getStatusName(Team team, Cypher cypher) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        return status.getCypherStatus().name();
    }

    private void saveNewStatus(final Status status, final CypherStatus newStatus) {
        status.setCypherStatus(newStatus);
        statusRepository.save(status);
    }

    public CypherStatus getCypherStatusForTeam(final Cypher cypher, final Team team) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        return status.getCypherStatus();
    }

    public void initializeStatusForTeamAndCypher(final Cypher cypher, final Team team) {
        Status status = new Status();
        status.setCypher(cypher);
        status.setTeam(team);
        status.setCypherStatus(null);
        statusRepository.save(status);
    }

    public List<Status> getAllByCypher(final Cypher cypher) {
        return statusRepository.findAllByCypher(cypher);
    }
}
