package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.service.HintService;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class CypherRepositoryTest {
    private static final int TESTED_STAGE = 3;
    private static final int TESTED_STAGE_NEGATIVE = -2;
    private static final int TESTED_STAGE_ZERO = 0;
    private static final Point TEST_LOCATION = new Point(59.9090442, 10.7423389);

    @Autowired
    CypherRepository cypherRepository;

    @Autowired
    HintService hintService;

    @Autowired
    ObjectGenerator generator;

    @Test
    public void countEmptyDatabaseTest() {
        assertEquals(0, cypherRepository.count());
    }

    @Test
    public void addNewCypherTest() {
        Cypher cypher = new Cypher(TEST_LOCATION, TESTED_STAGE);
        Cypher cypherNew = cypherRepository.save(cypher);

        assertNotNull(cypherNew);
        assertEquals(1, cypherRepository.count());
    }

    @Test
    public void countAllCyphersTest() {
        List<Cypher> cyphers = generator.generateCypherList();
        cypherRepository.saveAll(cyphers);

        assertEquals(ObjectGenerator.NUMBER_OF_CYPHERS, cypherRepository.count());
    }

    @Test
    public void deleteAllCyphersTest() {
        List<Cypher> cyphers = generator.generateCypherList();
        cypherRepository.saveAll(cyphers);
        cypherRepository.deleteAll();

        assertEquals(0, cypherRepository.count());
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void cypherWithEmptyLocationTest() {
        Cypher cypher = new Cypher(TESTED_STAGE);
        cypherRepository.save(cypher);

        assertEquals(0, cypherRepository.count());
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void cypherWithNegativeStageTest() {
        Cypher cypher = new Cypher(TEST_LOCATION, TESTED_STAGE_NEGATIVE);
        cypherRepository.save(cypher);

        assertEquals(0, cypherRepository.count());
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void cypherWithZeroStageTest() {
        Cypher cypher = new Cypher(TEST_LOCATION, TESTED_STAGE_ZERO);
        cypherRepository.save(cypher);

        assertEquals(0, cypherRepository.count());
    }
}
