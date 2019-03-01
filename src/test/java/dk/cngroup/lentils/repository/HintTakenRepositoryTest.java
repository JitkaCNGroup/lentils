package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.*;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private HintTakenRepository hintTakenRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private CypherService cypherService;

    @Autowired
    private HintService hintService;

    @Autowired
    private ObjectGenerator generator;

    @Test
    public void saveAllHintsTakenForOneCypherOneTeamTest() {
        Team team = teamService.save(new Team(generator.TEAM_NAME + TESTED_TEAM, 5, "1234"));
        Cypher cypher = cypherService.save(new Cypher(TESTED_STAGE));
        List<Hint> hints = hintService.saveAll(generator.generateHintsForCypher(cypher));
        List<HintTaken> hintsTaken = hints.stream()
                .map(hint -> createHintTaken(team, hint))
                .collect(Collectors.toList());

        hintTakenRepository.saveAll(hintsTaken);

        assertEquals(ObjectGenerator.NUMBER_OF_HINTS_FOR_CYPHER, hintTakenRepository.count());
    }

    private HintTaken createHintTaken(Team team, Hint hint) {
        return new HintTaken(team, hint);
    }
}