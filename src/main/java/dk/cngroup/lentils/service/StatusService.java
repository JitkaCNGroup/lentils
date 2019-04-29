package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.NextCypherNotFoundException;
import dk.cngroup.lentils.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private CypherService cypherService;

    @Autowired
    public StatusService(final StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Autowired
    public void setCypherService(final CypherService cypherService) {
        this.cypherService = cypherService;
    }

    public void markCypher(final Cypher cypher, final Team team, final CypherStatus cypherStatus) {
        updateStatus(cypher, team, cypherStatus);
        try {
            updateStatus(cypherService.getNext(cypher.getStage()),
                    team,
                    cypherStatus.getNextCypherStatus());
        } catch (NextCypherNotFoundException | IllegalStateException e) {
            // status of the last cypher has been changed -> there is no next cypher
        }
    }

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public int getStatusScore(final Team team, final Cypher cypher) {
        try {
            return getStatusByTeamAndCypher(team, cypher).getCypherStatus().getStatusValue();
        } catch (IllegalStateException e) {
            return 0;
        }
    }

    public CypherStatus getCypherStatusByTeamAndCypher(final Team team, final Cypher cypher) {
        return getStatusByTeamAndCypher(team, cypher).getCypherStatus();
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

    private void updateStatus(final Cypher cypher,
                              final Team team,
                              final CypherStatus cypherStatus) {
        Status status = getStatusByTeamAndCypher(team, cypher);
        saveNewStatus(status, cypherStatus);
    }

    private Status getStatusByTeamAndCypher(final Team team, final Cypher cypher) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        if (status == null) {
            throw new IllegalStateException("No status found for the given cypher and team in database");
        }

        return status;
    }

    private void saveNewStatus(final Status status, final CypherStatus newStatus) {
        status.setCypherStatus(newStatus);
        statusRepository.save(status);
    }

    public void deleteAllByCypherId(final Long cypherId) {
        statusRepository.deleteAllByCypherCypherId(cypherId);
    }
}
