package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.repository.TeamRepository;
import dk.cngroup.lentils.repository.UserRepository;
import dk.cngroup.lentils.util.UsernameUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class TeamServiceIntegrationTest {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectGenerator generator;

    private int numOfUsersBeforeTest;
    private int numOfTeamsBeforeTest;

    @Before
    public void setup() {
        numOfUsersBeforeTest = userRepository.findAll().size();
        numOfTeamsBeforeTest = teamRepository.findAll().size();
    }

    @Test
    public void addingOneTeamCorrectlyCreatesUser() {
        createAndSaveTeam("pump", "8888");

        assertEquals(numOfTeamsBeforeTest + 1, teamRepository.count());
        assertEquals(numOfUsersBeforeTest + 1, userRepository.count());
        assertThatTeamHasAssociatedCorrectUser("pump");
    }

    @Test
    public void addingFourTeamsCorrectlyCreatesUsers() {
        createAndSaveTeam("a", "1111");
        createAndSaveTeam("b","2222");
        createAndSaveTeam("c","3333");
        createAndSaveTeam("d","4444");

        assertEquals(numOfTeamsBeforeTest + 4, teamService.getAll().size());
        assertEquals(numOfUsersBeforeTest + 4, userRepository.findAll().size());
        assertThatTeamHasAssociatedCorrectUser("a");
        assertThatTeamHasAssociatedCorrectUser("b");
        assertThatTeamHasAssociatedCorrectUser("c");
        assertThatTeamHasAssociatedCorrectUser("d");
    }

    private void createAndSaveTeam(String name, String pin) {
        Team team = generator.generateTeamWithNameAndPin(name, pin);
        teamService.save(team);
    }

    private void assertThatTeamHasAssociatedCorrectUser(final String teamName) {
        final Team team = teamRepository.findByName(teamName);
        final User user = userRepository
                .findByUsername(UsernameUtils.generateUsername(teamName))
                .orElseThrow(() -> new RuntimeException("Cannot find user"));

        assertNotNull(team);
        assertEquals(team.getUser().getUserId(), user.getUserId());
        assertEquals(user.getTeam().getTeamId(), team.getTeamId());
    }
}