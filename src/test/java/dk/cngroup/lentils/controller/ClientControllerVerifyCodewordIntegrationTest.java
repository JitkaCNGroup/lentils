package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.*;
import dk.cngroup.lentils.entity.formEntity.Codeword;
import dk.cngroup.lentils.repository.*;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.utils.AssertionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Collections;

import static dk.cngroup.lentils.controller.ClientController.GAME_ENDED_ERROR_MSG;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class ClientControllerVerifyCodewordIntegrationTest {
    public static final String CORRECT_CODEWORD = "topgun";
    public static final String FALSE_CODEWORD = "firefly";
    private static final Point TEST_LOCATION = new Point(59.9090442, 10.7423389);
    private static final String HINT_NAME = "abcd";

    @Autowired
    private ClientController testedController;
    @Autowired
    private CypherRepository cypherRepository;
    @Autowired
    private HintRepository hintRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private FinalPlaceRepository finalPlaceRepository;
    @Autowired
    private ObjectGenerator generator;

    private BindingResult result;
    private Cypher cypher;
    private Team team;
    private User user;

    @Before
    public void setup() {
        result = mock(BindingResult.class);

        createTestCypher();
        createTestTeamAndUser();
        createCypherStatus(cypher, team, CypherStatus.PENDING);
    }

    @Test
    public void testCheckCorrectCodeword() {
        setupThatGameHasNotEnded();
        final Codeword codeword = createCodewordFormObject(CORRECT_CODEWORD);

        final String returnValue = executeTestedMethod(codeword);

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(team, cypher);
        assertEquals(CypherStatus.SOLVED, status.getCypherStatus());
    }

    @Test
    public void testIncorrectCodeword() {
        setupThatGameHasNotEnded();
        final Codeword codeword = createCodewordFormObject(CORRECT_CODEWORD + "_wrong");

        final String returnValue = executeTestedMethod(codeword);

        AssertionUtils.assertValueIsNotRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(team, cypher);
        assertEquals(CypherStatus.PENDING, status.getCypherStatus());
    }

    @Test
    public void testFalseCodeword() {
        setupThatGameHasNotEnded();
        final Codeword codeword = createCodewordFormObject(FALSE_CODEWORD);

        final String returnValue = executeTestedMethod(codeword);

        AssertionUtils.assertValueIsRedirection(returnValue);
        final Status status = statusRepository.findByTeamAndCypher(team, cypher);
        assertEquals(CypherStatus.PENDING, status.getCypherStatus());
        assertTrue(returnValue.contains("lets-play-a-game"));
    }

    @Test
    public void testCheckCodewordAfterGameHasEnded() {
        final Codeword codeword = createCodewordFormObject(CORRECT_CODEWORD);
        setupThatGameHasAlreadyEnded();
        ArgumentCaptor<FieldError> argument = ArgumentCaptor.forClass(FieldError.class);

        String returnValue = executeTestedMethod(codeword);

        verify(result).addError(argument.capture());
        assertEquals("guess", argument.getValue().getField());
        assertEquals(GAME_ENDED_ERROR_MSG, argument.getValue().getDefaultMessage());
        AssertionUtils.assertValueIsNotRedirection(returnValue);
    }

    private void createTestCypher() {
        Hint hint = new Hint(HINT_NAME, 5, cypher);
        hintRepository.save(hint);
        cypher = generator.generateValidCypher();
        cypher.setHints(Collections.singletonList(hint));
        cypherRepository.save(cypher);
    }

    private void createTestTeamAndUser() {
        team = generator.generateValidTeam();
        teamRepository.save(team);

        user = new User();
        user.setUsername("johnny");
        user.setPassword("dummy");
        user.setTeam(team);
        userRepository.save(user);
    }

    private void createCypherStatus(final Cypher cypher, final Team team, final CypherStatus value) {
        final Status status = new Status();
        status.setCypherStatus(value);
        status.setCypher(cypher);
        status.setTeam(team);
        statusRepository.save(status);
    }

    private CustomUserDetails getUserDetailsMock() {
        return new CustomUserDetails(user);
    }

    private String executeTestedMethod(final Codeword withCodeword) {
        return testedController.verifyCodeword(
                cypher.getCypherId(),
                withCodeword,
                getUserDetailsMock(),
                result,
                mock(Model.class));
    }

    private Codeword createCodewordFormObject(final String guess) {
        final Codeword  codeword = new Codeword();
        codeword.setGuess(guess);

        return codeword;
    }

    private void setupThatGameHasNotEnded() {
        final LocalDateTime time = LocalDateTime.now();

        createFinalPlace(time.plusHours(5));
    }

    private void setupThatGameHasAlreadyEnded() {
        final LocalDateTime time = LocalDateTime.now();

        createFinalPlace(time.minusHours(2));
    }

    private void createFinalPlace(LocalDateTime time) {
        final FinalPlace finalPlace = new FinalPlace();
        finalPlace.setLocation(TEST_LOCATION);
        finalPlace.setDescription("description");
        finalPlace.setOpeningTime(time);
        finalPlaceRepository.save(finalPlace);
    }
}
