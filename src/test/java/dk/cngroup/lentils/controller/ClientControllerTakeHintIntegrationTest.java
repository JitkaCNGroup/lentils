package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.*;
import dk.cngroup.lentils.repository.*;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.HintTakenService;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.utils.AssertionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class ClientControllerTakeHintIntegrationTest {
    private static final String CORRECT_CODEWORD = "topgun";
    private static final String FALSE_CODEWORD = "firefly";
    private static final Point TEST_LOCATION = new Point(59.9090442, 10.7423389);
    private static final String HINT_NAME = "abcd";
    private static final int HINT_VALUE = 5;

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
    @Autowired
    private HintTakenService hintTakenService;

    private BindingResult result;
    private Cypher cypher;
    private Team team;
    private User user;
    private Hint hint;

    @Before
    public void setup() {
        result = mock(BindingResult.class);

        createTestCypher();
        createTestTeamAndUser();
        createCypherStatus(cypher, team, CypherStatus.PENDING);
    }

    @Test
    public void testTakeHintCorrectly() {
        setupThatGameHasNotEnded();

        final String returnValue = executeTestedMethod();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final int hintScore = hintTakenService.getHintScore(team, cypher);
        assertEquals(HINT_VALUE, hintScore);
    }

    @Test
    public void testTakeHintForNonPendingStatus() {
        setupThatGameHasNotEnded();
        createCypherStatus(cypher, team, CypherStatus.SOLVED);

        final String returnValue = executeTestedMethod();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final int hintScore = hintTakenService.getHintScore(team, cypher);
        assertNotEquals(HINT_VALUE, hintScore);
    }

    @Test
    public void testTakeHintAfterGameHasEnded() {
        setupThatGameHasAlreadyEnded();

        String returnValue = executeTestedMethod();

        AssertionUtils.assertValueIsRedirection(returnValue);
        final int hintScore = hintTakenService.getHintScore(team, cypher);
        assertNotEquals(HINT_VALUE, hintScore);
    }

    private void createTestCypher() {
        hint = new Hint(HINT_NAME, HINT_VALUE, cypher);
        hintRepository.save(hint);
        cypher = generator.generateValidCypher();
        cypher.setCodeword(CORRECT_CODEWORD);
        cypher.setTrapCodeword(FALSE_CODEWORD);
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

    private String executeTestedMethod() {
                return testedController.getHint(hint.getHintId(), getUserDetailsMock(), cypher);
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
