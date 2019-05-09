package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.entity.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LentilsApplication.class)
@Transactional
public class GameLogicServiceIntegrationTest {

    @Autowired
    private TeamService teamService;
    @Autowired
    private CypherService cypherService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private GameLogicService gameLogicService;
    @Autowired
    private FinalPlaceService finalPlaceService;

    @Test
    public void shouldHavePassedAllCyphers() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.SKIPPED);
        assertTrue(gameLogicService.passedAllCyphers(team));
    }

    @Test
    public void shouldNotHavePassedAllCyphers() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.PENDING);
        assertFalse(gameLogicService.passedAllCyphers(team));
    }

    @Test
    public void passedTimeToViewFinalPlaceIsFalseWithNoFinalPlaceTest() {
        assertFalse(gameLogicService.passedTimeToViewFinalPlace());
    }

    @Test
    public void passedTimeToViewFinalPlaceIsTrueWithSetFinalPlaceTest() {
        setFinalPlaceTime(20L);
        assertTrue(gameLogicService.passedTimeToViewFinalPlace());
    }

    @Test
    public void gameIsNotInProgressTestWithNoFinalPlaceTest() {
        assertFalse(gameLogicService.isGameInProgress());
    }

    @Test
    public void gameIsInProgressTestWithSetFinalPlaceTest() {
        setFinalPlaceTime(20L);
        assertTrue(gameLogicService.isGameInProgress());
    }

    @Test
    public void playerIsAllowedToViewFinalPlaceWithNotAllCyphersPassedButPassedFinalPlaceTimeTest() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.PENDING);
        setFinalPlaceTime(20L);
        assertTrue(gameLogicService.allowPlayersToViewFinalPlace(team));
    }

    @Test
    public void playerIsAllowedToViewFinalPlaceWithNotPassedFinalPlaceTimeButAllCyphersPassedTest() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.SOLVED);
        setFinalPlaceTime(61L);
        assertTrue(gameLogicService.allowPlayersToViewFinalPlace(team));
    }

    @Test
    public void playerIsAllowedToViewFinalPlaceWithPassedFinalPlaceTimeAndAllCyphersPassedTest() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.SOLVED);
        setFinalPlaceTime(20L);
        assertTrue(gameLogicService.allowPlayersToViewFinalPlace(team));
    }

    @Test
    public void playerIsNotAllowedToViewFinalPlaceWithNotPassedFinalPlaceTimeAndAllCyphersNotPassedTest() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.PENDING);
        setFinalPlaceTime(61L);
        assertFalse(gameLogicService.allowPlayersToViewFinalPlace(team));
    }

    private Team setCyphersToSpecificStatusesForTeam(final CypherStatus cypherStatus1,
                                                     final CypherStatus cypherStatus2) {
        Team team = new Team("team", 4, "1111");
        teamService.save(team);
        Cypher cypher1 = new Cypher("first", 1, new Point(8,8), "yes", "http://go.com" );
        Cypher cypher2 = new Cypher("second", 2, new Point(8,8), "yees", "http://go.com");
        cypherService.save(cypher1);
        cypherService.save(cypher2);
        statusService.initializeStatusForTeamAndCypher(cypher1, team);
        statusService.initializeStatusForTeamAndCypher(cypher2, team);
        statusService.markCypher(cypher1, team, cypherStatus1);
        statusService.markCypher(cypher2, team, cypherStatus2);
        return team;
    }

    private void setFinalPlaceTime(final Long plusMinutes) {
        LocalDateTime openingTime = LocalDateTime.now().plusMinutes(plusMinutes);
        final FinalPlace finalPlace = new FinalPlace("desc", new Point(8.5, 5), openingTime);
        finalPlaceService.save(finalPlace);
    }
}
