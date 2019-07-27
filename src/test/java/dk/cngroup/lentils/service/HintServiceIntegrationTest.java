package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.HintRepository;
import dk.cngroup.lentils.repository.HintTakenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class HintServiceIntegrationTest {

    @Autowired
    private CypherService cypherService;
    @Autowired
    private HintService hintService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private HintTakenService hintTakenService;
    @Autowired
    private HintTakenRepository hintTakenRepository;

    @Autowired
    private HintRepository hintRepository;

    private ObjectGenerator generator = new ObjectGenerator();

    Cypher cypher;
    Team team;

    @Before
    public void setup() {
        cypher = generator.generateValidCypher();
        team = generator.generateValidTeam();

        cypherService.save(cypher);
        teamService.save(team);
    }

    @Test
    public void testDeleteById_whenTeamTakenThisHint() {
        final List<Hint> hints = generator.generateHintsForCypher(cypher);
        hintRepository.saveAll(hints);
        hintRepository.flush();

        final long initialCount = hintRepository.count();

        final Hint hint = hints.get(0);
        hintTakenService.takeHint(team, hint);
        hintTakenRepository.flush();

        hintService.deleteById(hint.getHintId());
        hintRepository.flush();

        assertEquals(initialCount - 1, hintRepository.count());
    }
}
