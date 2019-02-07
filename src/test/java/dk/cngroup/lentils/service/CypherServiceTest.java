package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {LentilsApplication.class})
public class CypherServiceTest
{
    //@InjectMocks
    @Autowired
    CypherService service;

    private static final Integer TESTED_STAGE = 4;
    private static final Integer NUM_OF_ALL_CYPHERS = 5;
    private static final String CODEWORD = "Codeword";
    private static final String CODEWORD_FALSE = "CodewordFalse";

    //@Mock
   // @Autowired
    //CypherRepository repository;
/*
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

*/
    @Before
    public void setUp() {
        service.deleteAllCyphers();
    }

    private Cypher getCypherForStage(Integer stage)
    {
        return service.saveNewCypher(new Cypher("Easy", stage, 49.0988161, 17.7519189, CODEWORD, "hledej dole"));
    }

    @Test
    public void addNewCypher()
    {
        //Cypher actualCypher = repository.save(newCypher);
        //when(repository.findByStage(3)).thenReturn(newCypher);

        Integer id  = service.addNewCypher(getCypherForStage(TESTED_STAGE));

        assertNotNull(id);
    }

    @Test
    public void getHintForStage()
    {
        Cypher cypher = getCypherForStage(TESTED_STAGE);

        String hint = service.getHintForStage(TESTED_STAGE);

        assertEquals (cypher.getHint(), hint);
    }

    @Test
    public void getNextCypher()
    {
        Cypher cypher1 = getCypherForStage(TESTED_STAGE);
        Cypher cypher2 = getCypherForStage(TESTED_STAGE + 1);

        Cypher cypher = service.getNextCypher(TESTED_STAGE);

        assertEquals(cypher2, cypher);
    }

    @Test
    public void checkCodeword()
    {
        getCypherForStage(TESTED_STAGE);

        assertTrue(service.checkCodeword(CODEWORD, TESTED_STAGE));
        assertFalse(service.checkCodeword(CODEWORD_FALSE, TESTED_STAGE));
    }
}