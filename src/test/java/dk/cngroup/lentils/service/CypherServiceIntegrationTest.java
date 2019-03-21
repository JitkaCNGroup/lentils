package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.repository.CypherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class CypherServiceIntegrationTest {

    @Autowired
    private CypherService testedService;

    @Autowired
    private CypherRepository cypherRepository;

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


    private Cypher getCypherWithStageNumber(final int stageNumber) {
        final Cypher cypher = new Cypher(
                "cypher",
                stageNumber,
                new Point(0,0),
                "codeword"
        );

        cypherRepository.save(cypher);

        return cypher;
    }
}
