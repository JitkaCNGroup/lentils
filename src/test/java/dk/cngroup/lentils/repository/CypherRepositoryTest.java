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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class CypherRepositoryTest
{
    private static final Integer TESTED_STAGE = 3;
    private static final Integer NUM_OF_ALL_CYPHERS = 5;

    @Autowired
    CypherRepository cypherRepository;

    @Before
    @After
    public void cleanDb() {
        cypherRepository.deleteAll();
    }

    @Test
    public void countEmptyDatabaseTest() {
        assertEquals(0, cypherRepository.count());
    }
    @Test
    public void addNewCypher()
    {
        long originalCount = cypherRepository.count();
        Cypher cypher = new Cypher("Easy", TESTED_STAGE, 49.0988161, 17.7519189, "abc123", "dole");
        cypherRepository.save(cypher);

        assertEquals(1, cypherRepository.count()- originalCount);
    }

    @Test
    public void getCypherForStage()
    {
        Cypher originalCypher = new Cypher(TESTED_STAGE);
        cypherRepository.save(originalCypher);

        Cypher cypher = cypherRepository.findByStage(TESTED_STAGE);

        assertNotNull(cypher);
        assertEquals(originalCypher, cypher);
    }

    @Test
    public void countAllCyphers()
    {
        List<Cypher> cyphers = new LinkedList<>();
        for (int i = 0; i < NUM_OF_ALL_CYPHERS; i++) {
            cyphers.add(new Cypher(i));
        }
        cypherRepository.saveAll(cyphers);

        assertEquals((long)NUM_OF_ALL_CYPHERS, cypherRepository.count());
    }
    @Test
    public void deleteAllCyphers()
    {
        List<Cypher> cyphers = new LinkedList<>();
        for (int i = 0; i < NUM_OF_ALL_CYPHERS; i++) {
            cyphers.add(new Cypher(i));
        }
        cypherRepository.saveAll(cyphers);

        cypherRepository.deleteAll();

        assertEquals(0, cypherRepository.count());
    }

}