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
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void getCurrentStageRangeOfAllTeamsTest() {
        Cypher cypher1 = new Cypher(new Point(1.0, 1.0), 1, "address1");
        Cypher cypher2 = new Cypher(new Point(1.0, 1.0), 2, "address2");
        Cypher cypher3 = new Cypher(new Point(1.0, 1.0), 3, "address3");
        Cypher cypher4 = new Cypher(new Point(1.0, 1.0), 4, "address4");
        cypherService.save(cypher1);
        cypherService.save(cypher2);
        cypherService.save(cypher3);
        cypherService.save(cypher4);
        Team team1 = new Team("team1", 4, "1111");
        Team team2 = new Team("team2", 4, "2222");
        Team team3 = new Team("team3", 4, "3333");
        Team team4 = new Team("team4", 4, "4444");
        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);
        teamRepository.save(team4);
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
        Cypher cypher1 = new Cypher(new Point(1.0, 1.0), 1, "address1");
        Cypher cypher2 = new Cypher(new Point(1.0, 1.0), 2, "address2");
        Cypher cypher3 = new Cypher(new Point(1.0, 1.0), 3, "address3");
        Cypher cypher4 = new Cypher(new Point(1.0, 1.0), 4, "address4");
        cypherService.save(cypher1);
        cypherService.save(cypher2);
        cypherService.save(cypher3);
        cypherService.save(cypher4);
        Team team1 = new Team("team1", 4, "1111");
        Team team2 = new Team("team2", 4, "2222");
        Team team3 = new Team("team3", 4, "3333");
        Team team4 = new Team("team4", 4, "4444");
        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);
        teamRepository.save(team4);
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
        Cypher cypher1 = new Cypher(new Point(1.0, 1.0), 1, "address1");
        Cypher cypher2 = new Cypher(new Point(1.0, 1.0), 2, "address2");
        Cypher cypher3 = new Cypher(new Point(1.0, 1.0), 3, "address3");
        Cypher cypher4 = new Cypher(new Point(1.0, 1.0), 4, "address4");
        cypherService.save(cypher1);
        cypherService.save(cypher2);
        cypherService.save(cypher3);
        cypherService.save(cypher4);
        Team team1 = new Team("team1", 4, "1111");
        Team team2 = new Team("team2", 4, "2222");
        Team team3 = new Team("team3", 4, "3333");
        Team team4 = new Team("team4", 4, "4444");
        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);
        teamRepository.save(team4);
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
}
