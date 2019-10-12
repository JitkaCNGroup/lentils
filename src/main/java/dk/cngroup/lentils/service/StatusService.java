package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.NextCypherNotFoundException;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.StatusRepositorySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private final CypherRepository cypherRepository;
    private CypherService cypherService;

    @Autowired
    public StatusService(final StatusRepository statusRepository,
                         final CypherRepository cypherRepository) {
        this.statusRepository = statusRepository;
        this.cypherRepository = cypherRepository;
    }

    @Autowired
    public void setCypherService(final CypherService cypherService) {
        this.cypherService = cypherService;
    }

    public void markCypher(final Cypher cypher, final Team team, final CypherStatus cypherStatus) {
        updateStatus(cypher, team, cypherStatus);
        if (restOfCyphersAreLocked(team, cypher)) {
            try {
                updateStatus(cypherService.getNext(cypher.getStage()), team, cypherStatus.getNextCypherStatus());
            } catch (NextCypherNotFoundException | IllegalStateException e) {
                // status of the last cypher has been changed -> there is no next cypher
            }
        }
    }

    public boolean restOfCyphersAreLocked(final Team team, final Cypher cypher) {
        List<Cypher> cypherList = cypherRepository.findByStageGreaterThanOrderByStageAsc(cypher.getStage());
        for (Cypher greaterCypher: cypherList) {
            if (!statusRepository.existsStatusByCypherAndTeamAndCypherStatus(
                    greaterCypher, team, CypherStatus.LOCKED)) {
                return false;
            }
        }
        return true;
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

    public boolean isStatusInDbByCypherAndTeam(final Cypher cypher, final Team team) {
        return statusRepository.existsStatusByCypherAndTeam(cypher, team);
    }

    public List<Status> getAllByCypher(final Cypher cypher) {
        return statusRepository.findAllByCypher(cypher);
    }

    public List<Status> getAllByTeam(final Team team) {
        return statusRepository.findByTeam(team);
    }

    public List<Status> getAllByTeamOrderByStage(final Team team) {
        return statusRepository.findByTeamOrderByCypherStageAsc(team);
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

    public List<Status> getPendingCyphers(final Team team) {
        return statusRepository.findByTeamAndCypherStatusOrderByCypherStageAsc(team, CypherStatus.PENDING);
    }

    public List<Status> getStatusesOfSearchedTeamsAtCypherWithStatus(final String searchName,
                                                                     final Cypher cypher,
                                                                     final CypherStatus cypherStatus,
                                                                     final boolean withCypherStatus) {

        Specification<Status> hasTeamNameSpec = StatusRepositorySpec.hasTeamName(searchName);
        Specification<Status> hasCypherSpec = StatusRepositorySpec.hasCypher(cypher);

        if (withCypherStatus) {
            return statusRepository.findAll(Specification
                    .where(hasTeamNameSpec)
                    .and(hasCypherSpec)
                    .and(StatusRepositorySpec.hasCypherStatus(cypherStatus)));
        } else {
            return statusRepository.findAll(Specification
                    .where(hasTeamNameSpec)
                    .and(hasCypherSpec)
                    .and(StatusRepositorySpec.hasNotCypherStatus(cypherStatus)));
        }
    }

    public void deleteAllByCypherId(final Long cypherId) {
        statusRepository.deleteAllByCypherCypherId(cypherId);
    }

    public void deleteAllByTeamId(final Long teamId) {
        statusRepository.deleteAllByTeamTeamId(teamId);
    }
}
