package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.repository.CypherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class CypherServiceTest {
    private static final int TESTED_STAGE = 3;
    private static final String CODEWORD = "Codeword";

    @InjectMocks
    CypherService service;

    @Mock
    CypherRepository repository;

    @Test
    public void addNewCypherTest() {
        Cypher cypher = service.save(getCypherForStage());

        assertNotNull(cypher);
    }

    @Test
    public void checkCodewordTest() {
        Cypher cypher = getCypherForStage();

        assertTrue(service.checkCodeword(cypher, CODEWORD));
    }

    private Cypher getCypherForStage(Integer stage) {
        Cypher cypher = new Cypher("blabla", stage, new Point(0,0), CODEWORD);
        when(repository.save(cypher)).thenReturn(cypher);

        return cypher;
    }

    private Cypher getCypherForStage() {
        return getCypherForStage(TESTED_STAGE);
    }
}
