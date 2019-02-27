package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.service.HintService;
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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class CypherRepositoryTest {
    private static final int TESTED_STAGE = 3;

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
    public void addNewCypher() {
        Cypher cypher = new Cypher(TESTED_STAGE);
        List<Hint> hints = generator.generateHintsForCypher(cypher);
        cypher.setHintsSet(new HashSet<>(hints));

        Cypher cypherNew = cypherRepository.save(cypher);

        assertNotNull(cypherNew);
        assertEquals(1, cypherRepository.count());
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
