package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.HintTakenRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    private StatusRepository statusRepository;
    private CypherService cypherService;
    private TeamService teamService;

    @Autowired
    public StatusService(StatusRepository statusRepository, CypherService cypherService, TeamService teamService) {
        this.statusRepository = statusRepository;
        this.cypherService = cypherService;
        this.teamService = teamService;
    }

    public void markCypherSolvedForTeam(Cypher cypher, Team team) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        saveNewStatus(status, CypherStatus.SOLVED);
    }

    public void markCypherSolvedForTeam(Long cypherId, Long teamId) {
        Cypher cypher = cypherService.findById(cypherId);
        Team team = teamService.findById(teamId);
        markCypherSolvedForTeam(cypher, team);
    }

    public void markCypherSkippedForTeam(Cypher cypher, Team team) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        saveNewStatus(status, CypherStatus.SKIPPED);
    }

    public void markCypherSkippedForTeam(Long cypherId, Long teamId) {
        Cypher cypher = cypherService.findById(cypherId);
        Team team = teamService.findById(teamId);
        markCypherSkippedForTeam(cypher, team);
    }

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public int getStatusScore(Team team, Cypher cypher) {
        Status status = statusRepository.findByTeamAndCypher(team, cypher);
        return status.getCypherStatus().getStatusValue();
    }

    private void saveNewStatus(Status status, CypherStatus newStatus) {
        status.setCypherStatus(newStatus);
        statusRepository.save(status);
    }
}
