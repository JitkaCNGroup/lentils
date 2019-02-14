package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class CypherRepositoryTest {

    @Autowired
    CypherRepository cypherRepository;

    @Autowired
    ObjectGenerator generator;

    @Test
    public void countEmptyDatabaseTest() {
        assertEquals(0, cypherRepository.count());
    }

    @Test
    public void addNewCypher() {
        Cypher cypher = generator.generateCypher();

        cypherRepository.save(cypher);

        assertEquals(1, cypherRepository.count());
    }

    @Test
    public void getCypherForStage() {
        Cypher originalCypher = new Cypher(ObjectGenerator.TESTED_STAGE);
        cypherRepository.save(originalCypher);

        Cypher cypher = cypherRepository.findByStage(ObjectGenerator.TESTED_STAGE);

        assertNotNull(cypher);
        assertEquals(originalCypher, cypher);
    }

    @Test
    public void countAllCyphers() {
        List<Cypher> cyphers = generator.generateCypherList();
        cypherRepository.saveAll(cyphers);

        assertEquals(ObjectGenerator.NUMBER_OF_CYPHERS, cypherRepository.count());
    }

    @Test
    public void deleteAllCyphers() {
        List<Cypher> cyphers = generator.generateCypherList();
        cypherRepository.saveAll(cyphers);

        cypherRepository.deleteAll();

        assertEquals(0, cypherRepository.count());
    }
}
