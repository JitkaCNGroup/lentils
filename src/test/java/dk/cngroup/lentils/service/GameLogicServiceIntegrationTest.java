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
    private static final Integer TEST_FINALPLACE_ACCESS_TIME = 60;
    private static final Long TEST_FINALPLACE_FINISH_TIME_20 = 20L;
    private static final Long TEST_FINALPLACE_FINISH_TIME_61 = 61L;

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
    @Autowired
    private ObjectGenerator generator;

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
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);
        assertTrue(gameLogicService.passedTimeToViewFinalPlace());
    }

    @Test
    public void gameIsNotInProgressTestWithNoFinalPlaceTest() {
        assertFalse(gameLogicService.isGameInProgress());
    }

    @Test
    public void gameIsInProgressTestWithSetFinalPlaceTest() {
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);
        assertTrue(gameLogicService.isGameInProgress());
    }

    @Test
    public void playerIsAllowedToViewFinalPlaceWithNotAllCyphersPassedButPassedFinalPlaceTimeTest() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.PENDING);
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);
        assertTrue(gameLogicService.allowPlayersToViewFinalPlace(team));
    }

    @Test
    public void playerIsAllowedToViewFinalPlaceWithNotPassedFinalPlaceTimeButAllCyphersPassedTest() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.SOLVED);
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_61);
        assertTrue(gameLogicService.allowPlayersToViewFinalPlace(team));
    }

    @Test
    public void playerIsAllowedToViewFinalPlaceWithPassedFinalPlaceTimeAndAllCyphersPassedTest() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.SOLVED);
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);
        assertTrue(gameLogicService.allowPlayersToViewFinalPlace(team));
    }

    @Test
    public void playerIsNotAllowedToViewFinalPlaceWithNotPassedFinalPlaceTimeAndAllCyphersNotPassedTest() {
        Team team = setCyphersToSpecificStatusesForTeam(CypherStatus.SOLVED, CypherStatus.PENDING);
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_61);
        assertFalse(gameLogicService.allowPlayersToViewFinalPlace(team));
    }

    @Test
    public void isFinalPlacePresentTest() {
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);
        assertTrue(gameLogicService.isFinalPlaceFinishTimePresent());
    }

    @Test
    public void isNotFinalPlacePresentTest() {
        assertFalse(gameLogicService.isFinalPlaceFinishTimePresent());
    }

    @Test
    public void isAllowedToStartGameTest() {
        cypherService.save(generator.generateValidCypher());
        teamService.save(generator.generateValidTeam());
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);

        assertTrue(gameLogicService.isAllowedToStartGame());
    }

    @Test
    public void isNotAllowedToStartGameNoFinalPlaceTest() {
        cypherService.save(generator.generateValidCypher());
        teamService.save(generator.generateValidTeam());;

        assertFalse(gameLogicService.isAllowedToStartGame());
    }

    @Test
    public void isNotAllowedToStartGameNoCyphersTest() {
        teamService.save(generator.generateValidTeam());
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);

        assertFalse(gameLogicService.isAllowedToStartGame());
    }

    @Test
    public void isGameAlreadyActivatedForTeamTest() {
        Team team = teamService.save(generator.generateValidTeam());
        Cypher cypher = cypherService.save(generator.generateValidCypher());
        statusService.initializeStatusForTeamAndCypher(cypher, team);
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);

        assertTrue(gameLogicService.isGameAlreadyActivatedForTeam(team));
    }

    @Test
    public void gameIsNotActivatedForOnlyTeamTest() {
        Team team = teamService.save(generator.generateValidTeam());

        assertFalse(gameLogicService.isGameAlreadyActivatedForTeam(team));
    }

    @Test
    public void gameIsNotActivatedForTeamNoStatusTest() {
        Team team = teamService.save(generator.generateValidTeam());
        cypherService.save(generator.generateValidCypher());
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);

        assertFalse(gameLogicService.isGameAlreadyActivatedForTeam(team));
    }

    @Test
    public void gameIsNotActivatedForTeamNoFinalPlaceTest() {
        Team team = teamService.save(generator.generateValidTeam());
        Cypher cypher = cypherService.save(generator.generateValidCypher());
        statusService.initializeStatusForTeamAndCypher(cypher, team);

        assertFalse(gameLogicService.isGameAlreadyActivatedForTeam(team));
    }

    @Test
    public void gameIsNotActivatedForTeamNoCyphersTest() {
        Team team = teamService.save(generator.generateValidTeam());
        setFinalPlaceTime(TEST_FINALPLACE_FINISH_TIME_20);

        assertFalse(gameLogicService.isGameAlreadyActivatedForTeam(team));
    }

    private Team setCyphersToSpecificStatusesForTeam(final CypherStatus cypherStatus1,
                                                     final CypherStatus cypherStatus2) {
        Team team = generator.generateValidTeam();
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
        LocalDateTime finishTime = LocalDateTime.now().plusMinutes(plusMinutes);
        LocalDateTime resultsTime = LocalDateTime.now().plusMinutes(plusMinutes + 15);
        final FinalPlace finalPlace = new FinalPlace("desc", new Point(8.5, 5), finishTime, resultsTime, TEST_FINALPLACE_ACCESS_TIME);
        finalPlaceService.save(finalPlace);
    }
}
