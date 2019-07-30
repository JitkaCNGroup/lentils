package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.dto.CodewordFormDTO;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.utils.AssertionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import static dk.cngroup.lentils.controller.ClientController.GAME_ENDED_ERROR_MSG;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class ClientControllerVerifyCodewordIntegrationTest extends AbstractClientControllerTest {

    private BindingResult result;

    @Before
    public void setup() {
        setupSharedFixture();

        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.PENDING, getFixtureTeam());
        result = mock(BindingResult.class);
    }

    @Test
    public void testCheckCorrectCodeword() {
        setThatGameHasNotEnded();
        final CodewordFormDTO codewordFormDto = createCodewordFormObject(CORRECT_CODEWORD);

        final String returnValue = executeTestedMethod(codewordFormDto);

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertEquals(CypherStatus.SOLVED, status.getCypherStatus());
    }

    @Test
    public void testIncorrectCodeword() {
        setThatGameHasNotEnded();
        final CodewordFormDTO codewordFormDto = createCodewordFormObject(CORRECT_CODEWORD + "_wrong");

        final String returnValue = executeTestedMethod(codewordFormDto);

        AssertionUtils.assertValueIsNotRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertEquals(CypherStatus.PENDING, status.getCypherStatus());
    }

    @Test
    public void testFalseCodeword() {
        setThatGameHasNotEnded();
        final CodewordFormDTO codewordFormDto = createCodewordFormObject(FALSE_CODEWORD);

        final String returnValue = executeTestedMethod(codewordFormDto);

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertEquals(CypherStatus.PENDING, status.getCypherStatus());
        assertTrue(returnValue.contains("trap"));
    }

    @Test
    public void testCheckCodewordAfterGameHasEnded() {
        final CodewordFormDTO codewordFormDto = createCodewordFormObject(CORRECT_CODEWORD);
        setThatGameHasAlreadyEnded();
        ArgumentCaptor<FieldError> argument = ArgumentCaptor.forClass(FieldError.class);

        String returnValue = executeTestedMethod(codewordFormDto);

        verify(result).addError(argument.capture());
        assertEquals("guess", argument.getValue().getField());
        assertEquals(GAME_ENDED_ERROR_MSG, argument.getValue().getDefaultMessage());
        AssertionUtils.assertValueIsNotRedirection(returnValue);
    }

    @Test
    public void testCheckCodewordForNonPendingStatus() {
        setThatGameHasNotEnded();
        setCypherStatusForTeam(getFixtureCypher(), CypherStatus.SKIPPED, getFixtureTeam());
        final CodewordFormDTO codewordFormDto = createCodewordFormObject(CORRECT_CODEWORD);

        final String returnValue = executeTestedMethod(codewordFormDto);

        AssertionUtils.assertValueIsNotRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(getFixtureTeam(), getFixtureCypher());
        assertNotEquals(CypherStatus.SOLVED, status.getCypherStatus());
        assertEquals(CypherStatus.SKIPPED, status.getCypherStatus());
    }

    private String executeTestedMethod(final CodewordFormDTO withCodewordFormDto) {
        return testedController.verifyCodeword(
                getFixtureCypher().getCypherId(),
                withCodewordFormDto,
                getUserDetailsMock(),
                result,
                mock(Model.class));
    }

    private CodewordFormDTO createCodewordFormObject(final String guess) {
        final CodewordFormDTO codewordFormDto = new CodewordFormDTO();
        codewordFormDto.setGuess(guess);

        return codewordFormDto;
    }
}
