package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class HintTakenRepositoryTest {
    private static final int TESTED_TEAM = 5;
    private static final int TESTED_STAGE = 3;
    private static final int TESTED_STAGE_2 = 2;
    private static final Point TEST_LOCATION = new Point(59.9090442, 10.7423389);
    private static final String TEST_MAP_ADDRESS = "https://goo.gl/maps/jsvj1SWFR3rVUi7F6";

    @Autowired
    private HintTakenRepository hintTakenRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private CypherService cypherService;

    @Autowired
    private HintService hintService;

    @Autowired
    private HintService hintTakenService;

    @Autowired
    private ObjectGenerator generator;

    @Test
    public void saveAllHintsTakenForOneCypherOneTeamTest() {
        Team team = teamService.save(new Team(generator.TEAM_NAME + TESTED_TEAM, 5, "1234"));
        Cypher cypher = cypherService.save(new Cypher(TEST_LOCATION, TESTED_STAGE, TEST_MAP_ADDRESS));
        List<Hint> hints = hintService.saveAll(generator.generateHintsForCypher(cypher));
        List<HintTaken> hintsTaken = hints.stream()
                .map(hint -> createHintTaken(team, hint))
                .collect(Collectors.toList());

        hintTakenRepository.saveAll(hintsTaken);

        assertEquals(ObjectGenerator.NUMBER_OF_HINTS_FOR_CYPHER, hintTakenRepository.count());
    }

    @Test
    public void countNumberOfHintsTakenByTeamWhileOneHintTakenTest() {
        Team team = teamService.save(new Team("dgdf", 4, "dsdd"));
        Cypher cypher = cypherService.save(new Cypher(TEST_LOCATION, TESTED_STAGE, TEST_MAP_ADDRESS));
        createAndSaveHintTaken(team, new Hint("d", 5, cypher));
        assertEquals(1, hintTakenRepository.count());
        assertEquals(1, hintTakenRepository.findByTeam(team).size());
    }

    @Test
    public void countNumberOfHintsTakenByTeamWhileFourHintsTakenTest() {
        Team team = teamService.save(new Team("aaa", 4, "eeee"));
        Cypher cypher1 = cypherService.save(new Cypher(TEST_LOCATION, TESTED_STAGE, TEST_MAP_ADDRESS));
        Cypher cypher2 = cypherService.save(new Cypher(TEST_LOCATION, TESTED_STAGE_2, TEST_MAP_ADDRESS));
        createAndSaveHintTaken(team, new Hint("a", 4, cypher1));
        createAndSaveHintTaken(team, new Hint("b", 3, cypher1));
        createAndSaveHintTaken(team, new Hint("c", 2, cypher2));
        createAndSaveHintTaken(team, new Hint("d", 1, cypher2));
        assertEquals(4, hintTakenRepository.count());
        assertEquals(4, hintTakenRepository.findByTeam(team).size());
    }

    @Test
    public void countNumberOfHintsTakenByTeamWhileSavingToAnotherTeamTest() {
        Team team1 = teamService.save(new Team("aaaa", 4, "eeee"));
        Team team2 = teamService.save(new Team("bbbbb", 6, "ccccc"));
        Cypher cypher1 = cypherService.save(new Cypher(TEST_LOCATION, TESTED_STAGE, TEST_MAP_ADDRESS));
        Cypher cypher2 = cypherService.save(new Cypher(TEST_LOCATION, TESTED_STAGE_2, TEST_MAP_ADDRESS));
        createAndSaveHintTaken(team1, new Hint("a", 4, cypher1));
        createAndSaveHintTaken(team2, new Hint("b", 3, cypher1));
        createAndSaveHintTaken(team2, new Hint("c", 2, cypher2));
        assertEquals(3, hintTakenRepository.count());
        assertEquals(1, hintTakenRepository.findByTeam(team1).size());
        assertEquals(2, hintTakenRepository.findByTeam(team2).size());
    }

    @Test
    public void countNumberOfHintsTakenByTeamWhileNoHintsTakenTest() {
        Team team = teamService.save(new Team("team", 5, "aeeaea"));
        Cypher cypher = cypherService.save(new Cypher(TEST_LOCATION, 88, TEST_MAP_ADDRESS));
        Hint hint = hintService.save(new Hint("oh no", 20, cypher));
        assertEquals(0, hintTakenRepository.count());
        assertEquals(0, hintTakenRepository.findByTeam(team).size());
    }

    private HintTaken createHintTaken(Team team, Hint hint) {
        return new HintTaken(team, hint);
    }

    private void createAndSaveHintTaken(Team team, Hint hint) {
        hintService.save(hint);
        HintTaken hintTaken = new HintTaken(team, hint);
        hintTakenRepository.save(hintTaken);
    }
}