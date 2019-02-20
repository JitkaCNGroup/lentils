package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.repository.HintRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class HintServiceTest {

    @InjectMocks
    HintService hintService;

    @Mock
    HintRepository hintRepository;

    @Autowired
    private CypherService cypherService;

    @Autowired
    private ObjectGenerator generator;

    private List<Cypher> cyphers;

    @Before
    public void setUp() throws Exception {
        cypherService.saveAll(generator.generateCypherList());
        cyphers = cypherService.getAll();
    }

    @Test
    public void getHintsForCypher() {
        Cypher cypher = getTestedCypher();

        List<Hint> hints = generator.generateHintList(cyphers);
        when(hintRepository.saveAll(hints)).thenReturn(hints);
        hintService.addAll(hints);

//        when(hintRepository.findByCypher(cypher))
//        List<Hint> hintsForCypher = hintService.getHintsForCypher(cypher);

    }

    @Test
    public void addHintForCypher() {
        Cypher cypher = getTestedCypher();
        Hint hint = new Hint("hint", 5, cypher);

        when(hintRepository.save(hint)).thenReturn(hint);

        assertEquals(hint, hintService.add(hint));
    }

    @Test
    public void deleteHintsForCypher() {
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
