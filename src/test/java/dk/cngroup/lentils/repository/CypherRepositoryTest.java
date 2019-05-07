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
    private static final String TEST_STRING = "Lorem ipsum";
    private static final String TEST_EMPTY_STRING = "";
    private static final String TOO_LONG_STRING = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. N1";
    private static final String TEST_MAP_ADDRESS = "https://goo.gl/maps/jsvj1SWFR3rVUi7F6";

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
        Cypher cypher = new Cypher(TEST_LOCATION, TESTED_STAGE, TEST_MAP_ADDRESS);
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
        cypherRepository.saveAndFlush(cypher);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void cypherWithNegativeStageTest() {
        Cypher cypher = new Cypher(TEST_LOCATION, TESTED_STAGE_NEGATIVE, TEST_MAP_ADDRESS);
        cypherRepository.saveAndFlush(cypher);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void cypherWithZeroStageTest() {
        Cypher cypher = new Cypher(TEST_LOCATION, TESTED_STAGE_ZERO, TEST_MAP_ADDRESS);
        cypherRepository.saveAndFlush(cypher);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void cypherWithTooLongBonusContentTest() {
        Cypher cypher = new Cypher(TEST_LOCATION, TESTED_STAGE, TOO_LONG_STRING, TEST_STRING, TEST_MAP_ADDRESS);
        cypherRepository.saveAndFlush(cypher);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void cypherWithTooLongPlaceDescriptionTest() {
        Cypher cypher = new Cypher(TEST_LOCATION, TESTED_STAGE, TEST_STRING, TOO_LONG_STRING, TEST_MAP_ADDRESS);
        cypherRepository.saveAndFlush(cypher);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void cypherWithEmptyMapAddressTest() {
        Cypher cypher = new Cypher(TEST_LOCATION, TESTED_STAGE, TEST_STRING, TOO_LONG_STRING, TEST_EMPTY_STRING);
        cypherRepository.saveAndFlush(cypher);
    }
}
