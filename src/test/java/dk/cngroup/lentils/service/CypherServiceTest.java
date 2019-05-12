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
@SpringBootTest(classes = {LentilsApplication.class})
public class CypherServiceTest {
    private static final int TESTED_STAGE = 3;
    private static final String CODEWORD = "Codeword";
    private static final String CODEWORD_WITH_CZECH_SPECIAL_CHARACTERS = "Příliš Žluťoučký kůň";
    private static final String CODEWORD_WITHOUT_CZECH_SPECIAL_CHARACTERS = "Prilis Zlutoucky kun";
    private static final String TEST_MAP_ADDRESS = "https://goo.gl/maps/jsvj1SWFR3rVUi7F6";
    private static final String CYPHER_NAME = "blabla";

    @InjectMocks
    CypherService service;

    @Mock
    CypherRepository repository;

    @Test
    public void addNewCypherTest() {
        Cypher cypher = service.save(getCypherForStage(CODEWORD));

        assertNotNull(cypher);
    }

    @Test
    public void checkCodewordTest() {
        Cypher cypher = getCypherForStage(CODEWORD);

        assertTrue(service.checkCodeword(cypher, CODEWORD));
    }

    @Test
    public void checkUpperCaseCodewordTest() {
        Cypher cypher = getCypherForStage(CODEWORD);
        String upperCaseCodeword = CODEWORD.toUpperCase();

        assertTrue(service.checkCodeword(cypher, upperCaseCodeword));
    }

    @Test
    public void checkLowerCaseCodewordTest() {
        Cypher cypher = getCypherForStage(CODEWORD);
        String lowerCaseCodeword = CODEWORD.toLowerCase();

        assertTrue(service.checkCodeword(cypher, lowerCaseCodeword));
    }

    @Test
    public void checkCodewordWithSpecialCzechCharactersTest() {
        Cypher cypher = getCypherForStage(CODEWORD_WITHOUT_CZECH_SPECIAL_CHARACTERS);

        assertTrue(service.checkCodeword(cypher, CODEWORD_WITH_CZECH_SPECIAL_CHARACTERS));
    }

    private Cypher getCypherForStage(Integer stage, String codeword) {
        Cypher cypher = new Cypher(CYPHER_NAME, stage, new Point(0,0), codeword, TEST_MAP_ADDRESS);
        when(repository.save(cypher)).thenReturn(cypher);

        return cypher;
    }

    private Cypher getCypherForStage(String codeword) {
        return getCypherForStage(TESTED_STAGE, codeword);
    }
}
