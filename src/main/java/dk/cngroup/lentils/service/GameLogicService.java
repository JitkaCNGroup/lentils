package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class GameLogicService {

    private final FinalPlaceService finalPlaceService;
    private final StatusService statusService;
    private final CypherService cypherService;
    private final TeamService teamService;

    @Autowired
    public GameLogicService(final FinalPlaceService finalPlaceService,
                            final StatusService statusService,
                            final CypherService cypherService,
                            final TeamService teamService) {
        this.finalPlaceService = finalPlaceService;
        this.statusService = statusService;
        this.cypherService = cypherService;
        this.teamService = teamService;
    }

    public boolean isGameInProgress() {
        final FinalPlace finalPlace = finalPlaceService.getFinalPlace();

        return finalPlace.getFinishTime() != null && finalPlace.getFinishTime().isAfter(LocalDateTime.now());
    }

    public boolean allowPlayersToViewFinalPlace(final Team team) {
        return (passedTimeToViewFinalPlace() || passedAllCyphers(team));
    }

    public boolean passedAllCyphers(final Team team) {
        List<Status> statusesOfTeam = statusService.getAllByTeam(team);
        return (!existsStatusForTeam(team, CypherStatus.PENDING) &&
                (!existsStatusForTeam(team, CypherStatus.LOCKED) && !statusesOfTeam.isEmpty()));
    }

    public boolean passedTimeToViewFinalPlace() {
        try {
            LocalDateTime finalPlaceFinishTime = finalPlaceService.getFinalPlace().getFinishTime();
            int accessMinutes = finalPlaceService.getFinalPlace().getAccessTime();

            return finalPlaceFinishTime.isBefore(LocalDateTime.now().plusMinutes(accessMinutes));
        } catch (Exception e) {
            return false;
        }
    }

    public LocalTime getFinalPlaceResultsTime() {
        FinalPlace finalPlace = finalPlaceService.getFinalPlace();
        return finalPlace.getResultsTime().toLocalTime();
    }

    public void initializeGameForTeam(final Team team) {
        List<Cypher> cyphers = cypherService.getAllCyphersOrderByStageAsc();
        if (!statusService.isStatusInDbByCypherAndTeam(cypherService.getFirstOrderByStageAsc(), team)) {
            cyphers.forEach(cypher -> {
                statusService.initializeStatusForTeamAndCypher(cypher, team);
            });
            statusService.markCypher(cypherService.getFirstOrderByStageAsc(), team, CypherStatus.PENDING);
        }
    }

    public void initializeGameForAllTeams() {
        teamService.getAll().forEach(team -> initializeGameForTeam(team));
    }

    private boolean existsStatusForTeam(final Team team, final CypherStatus cypherStatus) {
        return statusService.getAllByTeam(team).stream()
                .anyMatch(status -> status.getCypherStatus() == cypherStatus);
    }
}
