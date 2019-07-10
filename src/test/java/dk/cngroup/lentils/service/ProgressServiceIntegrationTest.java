package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LentilsApplication.class)
@Transactional
public class ProgressServiceIntegrationTest {

    @Autowired
    private ProgressService progressService;

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

    @Test
    public void getCurrentStageRangeOfAllTeamsTest() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypherWithStage(1));
        Cypher cypher2 = cypherService.save(generator.generateValidCypherWithStage(2));
        Cypher cypher3 = cypherService.save(generator.generateValidCypherWithStage(3));
        Cypher cypher4 = cypherService.save(generator.generateValidCypherWithStage(4));
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
        statusService.markCypher(cypher3, team3, CypherStatus.PENDING);
        statusService.markCypher(cypher4, team3, CypherStatus.LOCKED);

        statusService.markCypher(cypher1, team4, CypherStatus.SKIPPED);
        statusService.markCypher(cypher2, team4, CypherStatus.SOLVED);
        statusService.markCypher(cypher3, team4, CypherStatus.SOLVED);
        statusService.markCypher(cypher4, team4, CypherStatus.PENDING);

        int minStage = 2;
        int maxStage = 4;
        assertEquals(minStage, progressService.getCurrentStageRangeOfAllTeams().getMin());
        assertEquals(maxStage, progressService.getCurrentStageRangeOfAllTeams().getMax());
    }

    @Test
    public void shouldBeZeroGetNumberOfFinishedTeamsTest() {
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
        statusService.markCypher(cypher3, team3, CypherStatus.PENDING);
        statusService.markCypher(cypher4, team3, CypherStatus.LOCKED);

        statusService.markCypher(cypher1, team4, CypherStatus.SKIPPED);
        statusService.markCypher(cypher2, team4, CypherStatus.SOLVED);
        statusService.markCypher(cypher3, team4, CypherStatus.SOLVED);
        statusService.markCypher(cypher4, team4, CypherStatus.PENDING);

        assertEquals(0, progressService.getNumberOfFinishedTeams());
    }

    @Test
    public void shouldBeTwoGetNumberOfFinishedTeamsTest() {
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
        statusService.markCypher(cypher3, team4, CypherStatus.SOLVED);
        statusService.markCypher(cypher4, team4, CypherStatus.SOLVED);

        assertEquals(2, progressService.getNumberOfFinishedTeams());
    }


    @Test
    public void testGetTeamsWithPendingStatusAtFirstCypher() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("team3", "3333"));
        Team team4 = teamRepository.save(generator.generateTeamWithNameAndPin("team4", "4444"));

        gameLogicService.initializeGameForAllTeams();

        statusService.markCypher(cypher1, team1, CypherStatus.PENDING);
        statusService.markCypher(cypher1, team2, CypherStatus.LOCKED);
        statusService.markCypher(cypher1, team3, CypherStatus.SKIPPED);
        statusService.markCypher(cypher1, team4, CypherStatus.SOLVED);

        assertEquals(1, progressService.getSearchedTeamsWithSpecificStatusAtSpecificCypher("", cypher1, CypherStatus.PENDING, true).size());
    }

    @Test
    public void testGetTeamsExceptPendingStatusAtFirstCypher() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("team3", "3333"));
        Team team4 = teamRepository.save(generator.generateTeamWithNameAndPin("team4", "4444"));

        gameLogicService.initializeGameForAllTeams();

        statusService.markCypher(cypher1, team1, CypherStatus.PENDING);
        statusService.markCypher(cypher1, team2, CypherStatus.LOCKED);
        statusService.markCypher(cypher1, team3, CypherStatus.SKIPPED);
        statusService.markCypher(cypher1, team4, CypherStatus.SOLVED);

        assertEquals(3, progressService.getSearchedTeamsWithSpecificStatusAtSpecificCypher("", cypher1, CypherStatus.PENDING, false).size());
    }

    @Test
    public void testGetSearchedTeamsExceptPendingStatusAtFirstCypher() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("group3", "3333"));
        Team team4 = teamRepository.save(generator.generateTeamWithNameAndPin("group4", "4444"));

        gameLogicService.initializeGameForAllTeams();

        statusService.markCypher(cypher1, team1, CypherStatus.LOCKED);
        statusService.markCypher(cypher1, team2, CypherStatus.LOCKED);
        statusService.markCypher(cypher1, team3, CypherStatus.SKIPPED);
        statusService.markCypher(cypher1, team4, CypherStatus.SOLVED);

        assertEquals(2, progressService.getSearchedTeamsWithSpecificStatusAtSpecificCypher("team", cypher1, CypherStatus.PENDING, false).size());
    }

    @Test
    public void testGetSearchedTeamsWithPendingStatusAtFirstCypher() {
        Cypher cypher1 = cypherService.save(generator.generateValidCypher());
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("team1", "1111"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("team2", "2222"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("group3", "3333"));
        Team team4 = teamRepository.save(generator.generateTeamWithNameAndPin("group4", "4444"));

        gameLogicService.initializeGameForAllTeams();

        statusService.markCypher(cypher1, team1, CypherStatus.LOCKED);
        statusService.markCypher(cypher1, team2, CypherStatus.LOCKED);
        statusService.markCypher(cypher1, team3, CypherStatus.SKIPPED);
        statusService.markCypher(cypher1, team4, CypherStatus.SOLVED);

        assertEquals(2, progressService.getSearchedTeamsWithSpecificStatusAtSpecificCypher("group", cypher1, CypherStatus.PENDING, false).size());
    }
}
