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
        Status status = getTeamCypherStatus(team, cypher);
        saveNewStatus(status, cypherStatus);
    }

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public int getStatusScore(final Team team, final Cypher cypher) {
        return getTeamCypherStatus(team, cypher).getCypherStatus().getStatusValue();
    }

    private Status getTeamCypherStatus(final Team team, final Cypher cypher) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        if (status == null) {
            throw new IllegalStateException("No status found for the given cypher and team in database");
        }

        return status;
    }

    public String getStatusNameByTeamAndCypher(final Team team, final Cypher cypher) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        return status.getCypherStatus().name();
    }

    public CypherStatus getCypherStatusByTeamAndCypher(final Team team, final Cypher cypher) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        return status.getCypherStatus();
    }

    private void saveNewStatus(final Status status, final CypherStatus newStatus) {
        status.setCypherStatus(newStatus);
        statusRepository.save(status);
    }

    public void initializeStatusForTeamAndCypher(final Cypher cypher, final Team team) {
        Status status = new Status();
        status.setCypher(cypher);
        status.setTeam(team);
        status.setCypherStatus(CypherStatus.LOCKED);
        statusRepository.save(status);
    }

    public Boolean isStatusInDbByCypherAndTeam(final Cypher cypher, final Team team) {
        return statusRepository.existsStatusByCypherAndTeam(cypher, team);
    }

    public List<Status> getAllByCypher(final Cypher cypher) {
        return statusRepository.findAllByCypher(cypher);
    }

    public List<Status> getAllByTeam(final Team team) {
        return statusRepository.findByTeam(team);
    }
}
