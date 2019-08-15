package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.utils.AssertionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class ClientControllerCypherDetailIntegrationTest extends AbstractClientControllerTest {

    @Mock
    private Model model;

    @Before
    public void setup() {
        setupSharedFixture();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testCypherDetail_invalidCypherId() {
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.PENDING, getFixtureTeam());

        testedController.cypherDetail(
                getFixtureCypher().getCypherId() + 100,
                getUserDetailsMock(),
                model);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testCypherDetail_lockedCypher() {
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.LOCKED, getFixtureTeam());

        testedController.cypherDetail(
                getFixtureCypher().getCypherId(),
                getUserDetailsMock(),
                model);
    }

    @Test
    public void testCypherDetail_pendingCypher() {
        verifyCypherDetailWithAccessibleCypherStatus(CypherStatus.PENDING);
    }

    @Test
    public void testCypherDetail_solvedCypher() {
        verifyCypherDetailWithAccessibleCypherStatus(CypherStatus.SOLVED);
    }

    @Test
    public void testCypherDetail_skippedCypher() {
        verifyCypherDetailWithAccessibleCypherStatus(CypherStatus.SKIPPED);
    }

    private void verifyCypherDetailWithAccessibleCypherStatus(final CypherStatus value) {
        setCypherStatusForTeam(getFixtureCypher(), value, getFixtureTeam());

        final String returnValue = testedController.cypherDetail(
                getFixtureCypher().getCypherId(),
                getUserDetailsMock(),
                model);

        AssertionUtils.assertValueIsNotRedirection(returnValue);
        verify(model).addAttribute(eq("cypher"), eq(getFixtureCypher()));
    }
}
