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

import java.time.Year;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class CypherRepositoryTest
{
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
        Cypher cypher = new Cypher("Easy", 1, Year.now().getValue(), 49.0988161, 17.7519189);
        cypherRepository.save(cypher);

        assertEquals(1, cypherRepository.count()- originalCount);
    }

    @Test
    public void getCypherForStage()
    {
        int stage = 2;
        int year = Year.now().getValue();

        Cypher originalCypher = new Cypher(stage, year);
        cypherRepository.save(originalCypher);

        Cypher cypher = cypherRepository.findByStageAndYear(stage, year );

        assertNotNull(cypher);
        assertEquals(originalCypher, cypher);
    }

    @Test
    public void getNumberOfAllCyphers()
    {
        int numOfCyphers = 6;
        List<Cypher> cyphers = new LinkedList<>();
        for (int i = 0; i < numOfCyphers; i++) {
            cyphers.add(new Cypher(i, Year.now().getValue()));
        }
        cypherRepository.saveAll(cyphers);

        assertEquals(numOfCyphers, cypherRepository.count());
    }

}