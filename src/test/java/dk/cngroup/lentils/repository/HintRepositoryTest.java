package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.ObjectGenerator;
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
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class HintRepositoryTest {

    @Autowired
    HintRepository hintRepository;

    @Autowired
    CypherService cypherService;

    @Autowired
    ObjectGenerator generator;

    private List<Cypher> cyphers;

    @Before
    public void setUp() throws Exception {
        cypherService.saveAll(generator.generateCypherList());
        cyphers = cypherService.getAll();
    }

    @Test
    public void saveAll() {
        hintRepository.saveAll(generator.generateHintList(cyphers));

        assertEquals(ObjectGenerator.NUMBER_OF_CYPHERS * ObjectGenerator.NUMBER_OF_HINTS_FOR_CYPHER
                , hintRepository.count());
    }

    @Test
    public void add() {
        Cypher testedCypher = getTestedCypher();

        Hint hint = new Hint("text", 5, testedCypher);
        hintRepository.save(hint);

        assertEquals(1, hintRepository.count());
    }

    @Test
    public void deleteAll() {
        hintRepository.saveAll(generator.generateHintList(cyphers));

        hintRepository.deleteAll();

        assertEquals(0, hintRepository.count());
    }

    @Test
    public void getAll() {
        hintRepository.saveAll(generator.generateHintList(cyphers));

        List<Hint> hints = hintRepository.findAll();

        assertEquals(ObjectGenerator.NUMBER_OF_CYPHERS * ObjectGenerator.NUMBER_OF_HINTS_FOR_CYPHER
                , hints.size());
    }

    @Test
    public void getAllForCypher() {
        hintRepository.saveAll(generator.generateHintList(cyphers));
        Cypher cypher = getTestedCypher();

        List<Hint> hints = hintRepository.findByCypher(cypher);

        assertEquals(ObjectGenerator.NUMBER_OF_HINTS_FOR_CYPHER, hints.size());

    }

    private Cypher getTestedCypher() {
        for (Cypher cypher : cyphers) {
            if (cypher.getStage() == ObjectGenerator.TESTED_STAGE) {
                return cypher;
            }
        }
        return null;
    }
}