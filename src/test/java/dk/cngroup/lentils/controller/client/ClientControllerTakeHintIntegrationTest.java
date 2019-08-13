package dk.cngroup.lentils.controller.client;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.service.HintTakenService;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.utils.AssertionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class ClientControllerTakeHintIntegrationTest extends AbstractClientControllerTest {
    private static final String HINT_NAME = "abcd";
    private static final int HINT_VALUE = 5;

    @Autowired
    private HintTakenService hintTakenService;

    private Hint hint;

    @Before
    public void setup() {
        setupSharedFixture();

        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.PENDING, getFixtureTeam());
        hint = createTestHintForCypher(getFixtureCypher());
    }

    @Test
    public void testTakeHintCorrectly() {
        setThatGameHasNotEnded();

        final String returnValue = executeTestedMethod();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final int hintScore = hintTakenService.getHintScore(getFixtureTeam(), getFixtureCypher());
        assertEquals(HINT_VALUE, hintScore);
    }

    @Test
    public void testTakeHintForNonPendingStatus() {
        setThatGameHasNotEnded();
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.SOLVED, getFixtureTeam());

        final String returnValue = executeTestedMethod();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final int hintScore = hintTakenService.getHintScore(getFixtureTeam(), getFixtureCypher());
        assertNotEquals(HINT_VALUE, hintScore);
    }

    @Test
    public void testTakeHintAfterGameHasEnded() {
        setThatGameHasAlreadyEnded();

        String returnValue = executeTestedMethod();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final int hintScore = hintTakenService.getHintScore(getFixtureTeam(), getFixtureCypher());
        assertNotEquals(HINT_VALUE, hintScore);
    }

    private String executeTestedMethod() {
                return hintController.getHint(hint.getHintId(), getUserDetailsMock(), getFixtureCypher());
    }

    private Hint createTestHintForCypher(final Cypher cypher) {
        final Hint hint = new Hint(HINT_NAME, HINT_VALUE, cypher);
        cypher.setHints(Collections.singletonList(hint));

        return hintRepository.save(hint);
    }
}
