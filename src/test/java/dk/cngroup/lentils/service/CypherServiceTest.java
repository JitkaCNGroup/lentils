package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.repository.CypherRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static dk.cngroup.lentils.service.ObjectGenerator.TESTED_STAGE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class CypherServiceTest {

    private static final String CODEWORD = "Codeword";

    private static final String CODEWORD_FALSE = "CodewordFalse";

    @InjectMocks
    CypherService service;

    @Mock
    CypherRepository repository;

    @Autowired
    private ObjectGenerator generator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addNewCypher() {
        Cypher cypher = service.add(getCypherForStage());

        assertNotNull(cypher);
    }

    @Test
    public void getHintForStage() {
        Cypher cypher = getCypherForStage();

        when(repository.findByStage(TESTED_STAGE)).thenReturn(cypher);
        String hint = service.getHintForStage(TESTED_STAGE);

        assertEquals(cypher.getHint(), hint);
    }

    @Test
    public void getNextCypher() {
        Cypher cypher1 = getCypherForStage();
        Cypher cypher2 = getCypherForStage(TESTED_STAGE + 1);

        when(repository.findByStage(TESTED_STAGE)).thenReturn(cypher1);
        when(repository.findByStage(TESTED_STAGE + 1)).thenReturn(cypher2);
        Cypher cypher = service.getNext(TESTED_STAGE);

        assertEquals(cypher2, cypher);
    }

    @Test
    public void checkCodeword() {
        Cypher cypher = getCypherForStage();

        when(repository.findByStage(TESTED_STAGE)).thenReturn(cypher);

        assertTrue(service.checkCodeword(CODEWORD, TESTED_STAGE));
        assertFalse(service.checkCodeword(CODEWORD_FALSE, TESTED_STAGE));
    }

    private Cypher getCypherForStage(Integer stage) {
        Cypher cypher = generator.generateCypher();

        when(repository.save(cypher)).thenReturn(cypher);

        return cypher;
    }

    private Cypher getCypherForStage() {
        return getCypherForStage(TESTED_STAGE);
    }

}
