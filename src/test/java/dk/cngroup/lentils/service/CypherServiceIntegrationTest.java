package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.HintRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import dk.cngroup.lentils.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class CypherServiceIntegrationTest {

    @Autowired
    private CypherService testedService;
    @Autowired
    private CypherRepository cypherRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private HintRepository hintRepository;

    final ObjectGenerator objectGenerator = new ObjectGenerator();

    @Test
    public void getNextCypherWhenHavingSuccessiveNumberTest() {
        final Cypher first = getCypherWithStageNumber(1);
        final Cypher next = getCypherWithStageNumber(2);
        final Cypher random = getCypherWithStageNumber(3);

        final Cypher result = testedService.getNext(first.getStage());

        assertNotNull(result);
        assertEquals(next.getStage(), result.getStage());
    }

    @Test
    public void getNextCypherWhenHavingArbitraryStageNumberTest() {
        final Cypher random = getCypherWithStageNumber(1);
        final Cypher first = getCypherWithStageNumber(4);
        final Cypher next = getCypherWithStageNumber(9);

        final Cypher result = testedService.getNext(first.getStage());

        assertNotNull(result);
        assertEquals(next.getStage(), result.getStage());
    }

    @Test
    public void getNextCypherWhenNextDoesNotExistsTest() {
        final Cypher first = getCypherWithStageNumber(4);
        final Cypher last = getCypherWithStageNumber(9);

        final Cypher result = testedService.getNext(last.getStage());

        assertNull(result);
    }

    @Test
    public void getNextCypherWhenCyphersHasBeenCreatedInRandomOrder() {
        final Cypher first = getCypherWithStageNumber(1);
        final Cypher fourth = getCypherWithStageNumber(4);
        final Cypher third = getCypherWithStageNumber(3);
        final Cypher second = getCypherWithStageNumber(2);


        final Cypher result = testedService.getNext(first.getStage());

        assertNotNull(result);
        assertEquals(second.getStage(), result.getStage());
    }

    /**
     * This tests #65
     * Cypher with Status record could not be deleted due to incorrect FK constrains.
     */
    @Test
    public void deleteCypher() {
        final Cypher cypher = getCypherWithStageNumber(1);
        final Team team = objectGenerator.generateValidTeam();
        final Status status = new Status();
        status.setCypherStatus(CypherStatus.PENDING);
        status.setCypher(cypher);
        status.setTeam(team);

        cypher.setHints(hintRepository.saveAll(objectGenerator.generateHintsForCypher(cypher)));
        teamRepository.saveAndFlush(team);
        statusRepository.saveAndFlush(status);

        final long cypherCount =  cypherRepository.count();
        final long statusCount = statusRepository.count();
        assertEquals(1, cypherCount);
        assertEquals(1, statusRepository.count());
        assertEquals(3, hintRepository.count());

        testedService.deleteById(cypher.getCypherId());

        assertEquals(cypherCount - 1, cypherRepository.count());
        assertEquals(statusCount - 1, statusRepository.count());
        assertEquals(0, hintRepository.count());
    }

    private Cypher getCypherWithStageNumber(final int stageNumber) {
        final Cypher cypher = objectGenerator.generateValidCypher();
        cypher.setStage(stageNumber);

        return cypherRepository.save(cypher);
    }
}
