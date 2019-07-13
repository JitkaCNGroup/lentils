package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LentilsApplication.class)
@Transactional
public class StatisticsServiceIntegrationTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private CypherService cypherService;

    @Autowired
    private GameLogicService gameLogicService;

    @Autowired
    private ObjectGenerator generator;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private HintTakenService hintTakenService;

    @Autowired
    private HintService hintService;

    @Test
    public void getNumbersOfTakenHintsOfCyphersTest() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Cypher cypher2 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("team3", "3333"));

        Hint hint1 = hintService.save(new Hint("hint1", 3, cypher1));
        Hint hint2 = hintService.save(new Hint("hint2", 6, cypher1));
        Hint hint3 = hintService.save(new Hint("hint2", 3, cypher2));
        Hint hint4 = hintService.save(new Hint("hint3", 6, cypher2));

        hintTakenService.takeHint(team1, hint1);
        hintTakenService.takeHint(team1, hint2);
        hintTakenService.takeHint(team2, hint1);
        hintTakenService.takeHint(team3, hint2);

        hintTakenService.takeHint(team1, hint3);
        hintTakenService.takeHint(team1, hint4);
        hintTakenService.takeHint(team2, hint3);
        hintTakenService.takeHint(team2, hint4);
        hintTakenService.takeHint(team3, hint3);

        Map<Cypher, Integer> takenHintsOfAllCyphers = statisticsService.getNumberOfHintsTakenOfCyphers();

        assertEquals(4, takenHintsOfAllCyphers.get(cypher1).intValue());
        assertEquals(5, takenHintsOfAllCyphers.get(cypher2).intValue());
    }

    @Test
    public void getPointsOfAllTakenHintsOfCyphersTest() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Cypher cypher2 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("team3", "3333"));

        Hint hint1 = hintService.save(new Hint("hint1", 3, cypher1));
        Hint hint2 = hintService.save(new Hint("hint2", 6, cypher1));
        Hint hint3 = hintService.save(new Hint("hint3", 2, cypher2));
        Hint hint4 = hintService.save(new Hint("hint4", 4, cypher2));

        setHintsToCypher(cypher1, hint1, hint2);
        setHintsToCypher(cypher2, hint3, hint4);

        hintTakenService.takeHint(team1, hint1);
        hintTakenService.takeHint(team1, hint2);
        hintTakenService.takeHint(team2, hint1);
        hintTakenService.takeHint(team3, hint2);

        hintTakenService.takeHint(team1, hint3);
        hintTakenService.takeHint(team1, hint4);
        hintTakenService.takeHint(team2, hint3);
        hintTakenService.takeHint(team2, hint4);
        hintTakenService.takeHint(team3, hint3);

        Map<Cypher, Integer> pointsOfTakenHintsOfAllCyphers = statisticsService.getPointsOfHintsTakenOfCyphers();

        assertEquals(18, pointsOfTakenHintsOfAllCyphers.get(cypher1).intValue());
        assertEquals(14, pointsOfTakenHintsOfAllCyphers.get(cypher2).intValue());
    }

    @Test
    public void countOfPendingCyphersTest() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Cypher cypher2 = cypherService.save(generator.generateValidCypher());
        Cypher cypher3 = cypherService.save(generator.generateValidCypher());
        Cypher cypher4 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("team3", "3333"));
        Team team4 = teamRepository.save(generator.generateTeamWithNameAndPin("team4", "4444"));
        gameLogicService.initializeGameForAllTeams();
        statusService.markCypher(cypher1, team1, CypherStatus.LOCKED);
        statusService.markCypher(cypher2, team1, CypherStatus.LOCKED);
        statusService.markCypher(cypher3, team1, CypherStatus.LOCKED);
        statusService.markCypher(cypher4, team1, CypherStatus.LOCKED);

        statusService.markCypher(cypher1, team2, CypherStatus.SKIPPED);
        statusService.markCypher(cypher2, team2, CypherStatus.PENDING);
        statusService.markCypher(cypher3, team2, CypherStatus.LOCKED);
        statusService.markCypher(cypher4, team2, CypherStatus.LOCKED);

        statusService.markCypher(cypher1, team3, CypherStatus.SKIPPED);
        statusService.markCypher(cypher2, team3, CypherStatus.SOLVED);
        statusService.markCypher(cypher3, team3, CypherStatus.SOLVED);
        statusService.markCypher(cypher4, team3, CypherStatus.SOLVED);

        statusService.markCypher(cypher1, team4, CypherStatus.SKIPPED);
        statusService.markCypher(cypher2, team4, CypherStatus.SOLVED);
        statusService.markCypher(cypher3, team4, CypherStatus.PENDING);
        statusService.markCypher(cypher4, team4, CypherStatus.SOLVED);

        Map<Cypher, Integer> countOfPendingStatusesOfCyphers = statisticsService
                .getCountOfSpecificStatusesOfCyphers(CypherStatus.PENDING);

        assertEquals(0, countOfPendingStatusesOfCyphers.get(cypher1).intValue());
        assertEquals(1, countOfPendingStatusesOfCyphers.get(cypher2).intValue());
        assertEquals(1, countOfPendingStatusesOfCyphers.get(cypher3).intValue());
        assertEquals(0, countOfPendingStatusesOfCyphers.get(cypher4).intValue());
    }

    @Test
    public void cyphersStatisticsForGameNotStarted() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());

        Map<Cypher, Integer> countOfPendingStatusesOfCyphers = statisticsService
                .getCountOfSpecificStatusesOfCyphers(CypherStatus.PENDING);
        Map<Cypher, Integer> pointsOfTakenHintsOfAllCyphers = statisticsService.getPointsOfHintsTakenOfCyphers();
        Map<Cypher, Integer> takenHintsOfAllCyphers = statisticsService.getNumberOfHintsTakenOfCyphers();

        assertEquals(0, countOfPendingStatusesOfCyphers.get(cypher1).intValue());
        assertEquals(0, pointsOfTakenHintsOfAllCyphers.get(cypher1).intValue());
        assertEquals(0, takenHintsOfAllCyphers.get(cypher1).intValue());
    }

    @Test
    public void teamsStatisticsForGameNotStarted() {
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));

        Map<Team, Integer> countOfPendingStatusesOfTeams = statisticsService
                .getCountOfSpecificStatusesOfTeams(CypherStatus.PENDING);
        Map<Team, Integer> pointsOfTakenHintsOfAllTeams = statisticsService.getPointsOfHintsTakenOfTeams();
        Map<Team, Integer> takenHintsOfAllTeams = statisticsService.getNumberOfHintsTakenOfTeams();

        assertEquals(0, countOfPendingStatusesOfTeams.get(team1).intValue());
        assertEquals(0, pointsOfTakenHintsOfAllTeams.get(team1).intValue());
        assertEquals(0, takenHintsOfAllTeams.get(team1).intValue());
    }

    @Test
    public void getNumbersOfTakenHintsOfTeamsTest() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Cypher cypher2 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("team3", "3333"));

        Hint hint1 = hintService.save(new Hint("hint1", 3, cypher1));
        Hint hint2 = hintService.save(new Hint("hint2", 6, cypher1));
        Hint hint3 = hintService.save(new Hint("hint3", 3, cypher2));
        Hint hint4 = hintService.save(new Hint("hint4", 6, cypher2));

        hintTakenService.takeHint(team1, hint1);
        hintTakenService.takeHint(team1, hint2);
        hintTakenService.takeHint(team2, hint1);
        hintTakenService.takeHint(team3, hint2);

        hintTakenService.takeHint(team1, hint3);
        hintTakenService.takeHint(team1, hint4);
        hintTakenService.takeHint(team2, hint3);
        hintTakenService.takeHint(team2, hint4);
        hintTakenService.takeHint(team3, hint3);

        Map<Team, Integer> takenHintsOfAllTeams = statisticsService.getNumberOfHintsTakenOfTeams();

        assertEquals(4, takenHintsOfAllTeams.get(team1).intValue());
        assertEquals(3, takenHintsOfAllTeams.get(team2).intValue());
    }

    @Test
    public void getPointsOfAllTakenHintsOfTeamsTest() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Cypher cypher2 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("team3", "3333"));

        Hint hint1 = hintService.save(new Hint("hint1", 3, cypher1));
        Hint hint2 = hintService.save(new Hint("hint2", 6, cypher1));
        Hint hint3 = hintService.save(new Hint("hint3", 2, cypher2));
        Hint hint4 = hintService.save(new Hint("hint4", 4, cypher2));

        setHintsToCypher(cypher1, hint1, hint2);
        setHintsToCypher(cypher2, hint3, hint4);

        hintTakenService.takeHint(team1, hint1);
        hintTakenService.takeHint(team1, hint2);
        hintTakenService.takeHint(team2, hint1);
        hintTakenService.takeHint(team3, hint2);

        hintTakenService.takeHint(team1, hint3);
        hintTakenService.takeHint(team1, hint4);
        hintTakenService.takeHint(team2, hint3);
        hintTakenService.takeHint(team2, hint4);
        hintTakenService.takeHint(team3, hint3);

        Map<Team, Integer> pointsOfTakenHintsOfAllTeams = statisticsService.getPointsOfHintsTakenOfTeams();

        assertEquals(15, pointsOfTakenHintsOfAllTeams.get(team1).intValue());
        assertEquals(9, pointsOfTakenHintsOfAllTeams.get(team2).intValue());
        assertEquals(8, pointsOfTakenHintsOfAllTeams.get(team3).intValue());
    }

    @Test
    public void countOfSolvedCyphersOfTeamsTest() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Cypher cypher2 = cypherService.save(generator.generateValidCypher());
        Cypher cypher3 = cypherService.save(generator.generateValidCypher());
        Cypher cypher4 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("team3", "3333"));
        Team team4 = teamRepository.save(generator.generateTeamWithNameAndPin("team4", "4444"));
        gameLogicService.initializeGameForAllTeams();
        statusService.markCypher(cypher1, team1, CypherStatus.LOCKED);
        statusService.markCypher(cypher2, team1, CypherStatus.LOCKED);
        statusService.markCypher(cypher3, team1, CypherStatus.LOCKED);
        statusService.markCypher(cypher4, team1, CypherStatus.LOCKED);

        statusService.markCypher(cypher1, team2, CypherStatus.SKIPPED);
        statusService.markCypher(cypher2, team2, CypherStatus.PENDING);
        statusService.markCypher(cypher3, team2, CypherStatus.LOCKED);
        statusService.markCypher(cypher4, team2, CypherStatus.LOCKED);

        statusService.markCypher(cypher1, team3, CypherStatus.SKIPPED);
        statusService.markCypher(cypher2, team3, CypherStatus.SOLVED);
        statusService.markCypher(cypher3, team3, CypherStatus.SOLVED);
        statusService.markCypher(cypher4, team3, CypherStatus.SOLVED);

        statusService.markCypher(cypher1, team4, CypherStatus.SKIPPED);
        statusService.markCypher(cypher2, team4, CypherStatus.SOLVED);
        statusService.markCypher(cypher3, team4, CypherStatus.PENDING);
        statusService.markCypher(cypher4, team4, CypherStatus.SOLVED);

        Map<Team, Integer> countOfSolvedCyphersOfTeams = statisticsService
                .getCountOfSpecificStatusesOfTeams(CypherStatus.SOLVED);

        assertEquals(0, countOfSolvedCyphersOfTeams.get(team1).intValue());
        assertEquals(0, countOfSolvedCyphersOfTeams.get(team2).intValue());
        assertEquals(3, countOfSolvedCyphersOfTeams.get(team3).intValue());
        assertEquals(2, countOfSolvedCyphersOfTeams.get(team4).intValue());
    }

    private void setHintsToCypher(final Cypher cypher, final Hint hint1, final Hint hint2) {
        List<Hint> hints = new ArrayList<>();
        hints.add(hint1);
        hints.add(hint2);
        cypher.setHints(hints);
    }
}