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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = {LentilsApplication.class})
public class CypherServiceTest {
    private static final String CODEWORD = "Codeword";
    private static final String CODEWORD_WITH_CZECH_SPECIAL_CHARACTERS = "Příliš Žluťoučký kůň";
    private static final String CODEWORD_WITHOUT_CZECH_SPECIAL_CHARACTERS = "Prilis Zlutoucky kun";

    @InjectMocks
    CypherService service;
    @Mock
    CypherRepository repository;

    ObjectGenerator generator = new ObjectGenerator();

    @Test
    public void addNewCypherTest() {
        final Cypher cypher = generator.generateValidCypher();
        when(repository.save(eq(cypher))).thenReturn(cypher);

        Cypher result = service.save(cypher);

        assertEquals(cypher, result);
    }

    @Test
    public void checkCodewordTest_exactCodeword() {
        Cypher cypher = generator.generateValidCypher();
        cypher.setCodeword(CODEWORD);

        assertTrue(service.checkCodeword(cypher, CODEWORD));
    }

    @Test
    public void checkUpperCaseCodewordTest() {
        Cypher cypher = generator.generateValidCypher();
        cypher.setCodeword(CODEWORD);
        String upperCaseCodeword = CODEWORD.toUpperCase();

        assertTrue(service.checkCodeword(cypher, upperCaseCodeword));
    }

    @Test
    public void checkLowerCaseCodewordTest() {
        Cypher cypher = generator.generateValidCypher();
        cypher.setCodeword(CODEWORD);
        String lowerCaseCodeword = CODEWORD.toLowerCase();

        assertTrue(service.checkCodeword(cypher, lowerCaseCodeword));
    }

    @Test
    public void checkCodewordWithSpecialCzechCharactersTest() {
        Cypher cypher = generator.generateValidCypher();
        cypher.setCodeword(CODEWORD_WITHOUT_CZECH_SPECIAL_CHARACTERS);

        assertTrue(service.checkCodeword(cypher, CODEWORD_WITH_CZECH_SPECIAL_CHARACTERS));
    }
}
