package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.utils.AssertionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class ClientControllerSkipCypherIntegrationTest extends AbstractClientControllerTest {

    @Before
    public void setup() {
        setupSharedFixture();

        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.PENDING, getFixtureTeam());
    }

    @Test
    public void testSkipCypherCorrectly() {
        setThatGameHasNotEnded();

        final String returnValue = executeTestedMethod();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertEquals(CypherStatus.SKIPPED, status.getCypherStatus());
    }

    @Test
    public void testSkipCypherForNonPendingStatus() {
        setThatGameHasNotEnded();
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.SOLVED, getFixtureTeam());

        final String returnValue = executeTestedMethod();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertNotEquals(CypherStatus.SKIPPED, status.getCypherStatus());
        assertEquals(CypherStatus.SOLVED, status.getCypherStatus());
    }

    @Test
    public void testSkipCypherAfterGameHasEnded() {
        setThatGameHasAlreadyEnded();

        String returnValue = executeTestedMethod();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertNotEquals(CypherStatus.SKIPPED, status.getCypherStatus());
    }

    private String executeTestedMethod() {
        return testedController.skipCypher(getFixtureCypher(), getUserDetailsMock());
    }
}
