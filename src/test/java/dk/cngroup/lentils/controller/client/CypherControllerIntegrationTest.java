package dk.cngroup.lentils.controller.client;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.dto.CodewordFormDTO;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.utils.AssertionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class CypherControllerIntegrationTest extends AbstractClientControllerTest {

    @Autowired
    protected CypherController cypherController;

    private BindingResult result;

    @Mock
    private Model model;

    @Before
    public void setup() {
        setupSharedFixture();
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.PENDING, getFixtureTeam());
        result = mock(BindingResult.class);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testCypherDetail_invalidCypherId() {
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.PENDING, getFixtureTeam());

        cypherController.cypherDetail(
                getFixtureCypher().getCypherId() + 100,
                getUserDetailsMock(),
                model);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testCypherDetail_lockedCypher() {
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.LOCKED, getFixtureTeam());

        cypherController.cypherDetail(
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

    @Test
    public void testSkipCypherCorrectly() {
        setThatGameHasNotEnded();

        final String returnValue = executeSkipCypher();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertEquals(CypherStatus.SKIPPED, status.getCypherStatus());
    }

    @Test
    public void testSkipCypherForNonPendingStatus() {
        setThatGameHasNotEnded();
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.SOLVED, getFixtureTeam());

        final String returnValue = executeSkipCypher();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertNotEquals(CypherStatus.SKIPPED, status.getCypherStatus());
        assertEquals(CypherStatus.SOLVED, status.getCypherStatus());
    }

    @Test
    public void testSkipCypherAfterGameHasEnded() {
        setThatGameHasAlreadyEnded();

        String returnValue = executeSkipCypher();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertNotEquals(CypherStatus.SKIPPED, status.getCypherStatus());
    }


    @Test
    public void testCheckCorrectCodeword() {
        setThatGameHasNotEnded();
        final CodewordFormDTO codewordFormDto = createCodewordFormDTO(CORRECT_CODEWORD);

        final String returnValue = executeVerifyCodeword(codewordFormDto);

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertEquals(CypherStatus.SOLVED, status.getCypherStatus());
    }

    @Test
    public void testIncorrectCodeword() {
        setThatGameHasNotEnded();
        final CodewordFormDTO codewordFormDto = createCodewordFormDTO(CORRECT_CODEWORD + "_wrong");

        final String returnValue = executeVerifyCodeword(codewordFormDto);

        AssertionUtils.assertValueIsNotRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertEquals(CypherStatus.PENDING, status.getCypherStatus());
    }

    @Test
    public void testFalseCodeword() {
        setThatGameHasNotEnded();
        final CodewordFormDTO codewordFormDto = createCodewordFormDTO(FALSE_CODEWORD);

        final String returnValue = executeVerifyCodeword(codewordFormDto);

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertEquals(CypherStatus.PENDING, status.getCypherStatus());
        assertTrue(returnValue.contains("trap"));
    }

    @Test
    public void testCheckCodewordAfterGameHasEnded() {
        final CodewordFormDTO codewordFormDto = createCodewordFormDTO(CORRECT_CODEWORD);
        setThatGameHasAlreadyEnded();
        ArgumentCaptor<FieldError> argument = ArgumentCaptor.forClass(FieldError.class);

        String returnValue = executeVerifyCodeword(codewordFormDto);

        verify(result).addError(argument.capture());
        assertEquals("guess", argument.getValue().getField());
        assertEquals("The game has already ended", argument.getValue().getDefaultMessage());
        AssertionUtils.assertValueIsNotRedirection(returnValue);
    }

    @Test
    public void testCheckCodewordForNonPendingStatus() {
        setThatGameHasNotEnded();
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.SKIPPED, getFixtureTeam());
        final CodewordFormDTO codewordFormDto = createCodewordFormDTO(CORRECT_CODEWORD);

        final String returnValue = executeVerifyCodeword(codewordFormDto);

        AssertionUtils.assertValueIsNotRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertNotEquals(CypherStatus.SOLVED, status.getCypherStatus());
        assertEquals(CypherStatus.SKIPPED, status.getCypherStatus());
    }

    private String executeVerifyCodeword(final CodewordFormDTO withCodewordFormDto) {
        return cypherController.verifyCodeword(
                getFixtureCypher().getCypherId(),
                withCodewordFormDto,
                getUserDetailsMock(),
                result,
                mock(Model.class));
    }

    private CodewordFormDTO createCodewordFormDTO(final String guess) {
        final CodewordFormDTO codewordFormDto = new CodewordFormDTO();
        codewordFormDto.setGuess(guess);

        return codewordFormDto;
    }

    private String executeSkipCypher() {
        return cypherController.skipCypher(getFixtureCypher().getCypherId(), getUserDetailsMock());
    }

    private void verifyCypherDetailWithAccessibleCypherStatus(final CypherStatus value) {
        setCypherStatusForTeam(getFixtureCypher(), value, getFixtureTeam());

        final String returnValue = cypherController.cypherDetail(
                getFixtureCypher().getCypherId(),
                getUserDetailsMock(),
                model);

        AssertionUtils.assertValueIsNotRedirection(returnValue);
        verify(model).addAttribute(eq("cypher"), eq(getFixtureCypher()));
    }
}
