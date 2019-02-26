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
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

import static dk.cngroup.lentils.service.ObjectGenerator.CODEWORD;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class HintRepositoryTest {
    private static final int TESTED_STAGE = 3;

    @Autowired
    private HintRepository hintRepository;

    @Autowired
    private CypherService cypherService;

    @Autowired
    private ObjectGenerator generator;

    private List<Cypher> cyphers;

    @Before
    public void setUp() {
        cypherService.saveAll(generator.generateCypherList());
        cyphers = cypherService.getAll();
    }

    @Test
    public void saveAll() {
        hintRepository.saveAll(generator.generateHintsForCypher(cyphers));

        int expected = ObjectGenerator.NUMBER_OF_CYPHERS * ObjectGenerator.NUMBER_OF_HINTS_FOR_CYPHER;
        assertEquals(expected, hintRepository.count());
    }

    @Test
    public void add() {
        Cypher cypher = new Cypher("Easy", TESTED_STAGE, new Point(49.0988161, 17.7519189), CODEWORD);
        Hint hint = new Hint("text", 5, cypher);
        hintRepository.save(hint);

        assertEquals(1, hintRepository.count());
    }

    @Test
    public void deleteAll() {
        hintRepository.saveAll(generator.generateHintsForCypher(cyphers));
        hintRepository.deleteAll();

        assertEquals(0, hintRepository.count());
    }

    @Test
    public void getAll() {
        hintRepository.saveAll(generator.generateHintsForCypher(cyphers));
        List<Hint> hints = hintRepository.findAll();

        int expected = ObjectGenerator.NUMBER_OF_CYPHERS * ObjectGenerator.NUMBER_OF_HINTS_FOR_CYPHER;
        assertEquals(expected, hints.size());
    }

    @Test
    public void getAllForCypher() {
        Cypher cypher = cypherService.getByStage(TESTED_STAGE);
        cypher.setHintsSet(new HashSet<Hint>(generator.generateHintsForCypher(cyphers)));
        cypherService.save(cypher);

        List<Hint> hints = hintRepository.findByCypher(cypher);

        assertEquals(ObjectGenerator.NUMBER_OF_HINTS_FOR_CYPHER, hints.size());
    }
}