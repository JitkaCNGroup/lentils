package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void shouldHavePassedAllCyphers() {
        Team team = new Team("team", 4, "1111");
        teamService.save(team);
        Cypher cypher1 = new Cypher("first", 1, new Point(8,8), "yes");
        Cypher cypher2 = new Cypher("second", 2, new Point(8,8), "yees");
        cypherService.save(cypher1);
        cypherService.save(cypher2);
        statusService.initializeStatusForTeamAndCypher(cypher1, team);
        statusService.initializeStatusForTeamAndCypher(cypher2, team);
        statusService.markCypher(cypher1, team, CypherStatus.SOLVED);
        statusService.markCypher(cypher2, team, CypherStatus.SKIPPED);
        assertTrue(gameLogicService.passedAllCyphers(team));
    }

    @Test
    public void shouldNotHavePassedAllCyphers() {
        Team team = new Team("team", 4, "1111");
        teamService.save(team);
        Cypher cypher1 = new Cypher("first", 1, new Point(8,8), "yes");
        Cypher cypher2 = new Cypher("second", 2, new Point(8,8), "yees");
        cypherService.save(cypher1);
        cypherService.save(cypher2);
        statusService.initializeStatusForTeamAndCypher(cypher1, team);
        statusService.initializeStatusForTeamAndCypher(cypher2, team);
        statusService.markCypher(cypher1, team, CypherStatus.SOLVED);
        statusService.markCypher(cypher2, team, CypherStatus.PENDING);
        assertFalse(gameLogicService.passedAllCyphers(team));
    }

    @Test
    public void passedTimeToViewFinalPlaceIsFalseWithNoFinalPlaceTest() {
        assertFalse(gameLogicService.passedTimeToViewFinalPlace());
    }
}
