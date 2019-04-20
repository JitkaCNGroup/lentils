package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import dk.cngroup.lentils.repository.UserRepository;
import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.utils.AssertionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
@Transactional
public class ClientControllerCypherDetailIntegrationTest {
    @Autowired
    private ClientController testedController;
    @Autowired
    private CypherRepository cypherRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;

    @Mock
    private Model model;

    private Cypher cypher;
    private Team team;
    private User user;

    @Before
    public void setup() {
        createTestTeamAndUser();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testCypherDetail_invalidCypherId() {
        createCypherWithStatus(CypherStatus.PENDING);

        testedController.cypherDetail(
                cypher.getCypherId() + 100,
                getUserDetailsMock(),
                model);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testCypherDetail_lockedCypher() {
        createCypherWithStatus(CypherStatus.LOCKED);

        testedController.cypherDetail(
                cypher.getCypherId(),
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

    private void createCypherWithStatus(final CypherStatus value) {
        cypher = new Cypher();
        cypher.setCodeword("dummy");
        cypherRepository.save(cypher);

        final Status status = new Status();
        status.setCypherStatus(value);
        status.setCypher(cypher);
        status.setTeam(team);
        statusRepository.save(status);
    }

    private void createTestTeamAndUser() {
        team = new Team();
        team.setName("testTeam");
        team.setPin("1234");
        team.setNumOfMembers(5);
        teamRepository.save(team);

        user = new User();
        user.setUsername("johnny");
        user.setPassword("dummy");
        user.setTeam(team);
        userRepository.save(user);
    }

    private CustomUserDetails getUserDetailsMock() {
        return new CustomUserDetails(user);
    }

    private void verifyCypherDetailWithAccessibleCypherStatus(final CypherStatus value) {
        createCypherWithStatus(value);

        final String returnValue = testedController.cypherDetail(
                cypher.getCypherId(),
                getUserDetailsMock(),
                model);

        AssertionUtils.assertValueIsNotRedirection(returnValue);
        verify(model).addAttribute(eq("cypher"), eq(cypher));
    }
}
