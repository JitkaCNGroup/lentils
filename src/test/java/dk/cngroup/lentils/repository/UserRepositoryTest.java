package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.TeamService;
import dk.cngroup.lentils.util.UsernameUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class})
public class UserRepositoryTest {
    private static final String TESTED_TEAM_NAME = "Team42";
    private static final String TEAM_PIN = "1234";
    private static final String TEST_USERNAME = "TestedUsername";
    private static final String TEST_PASSWORD = "SecretPassword";
    private static final String SPECIAL_TEAM_NAME = "Šťastné veselé chlupaté kočky domácí";
    private static final String GENERATED_USERNAME = "stastne_vesele_chlupate_kocky";

    @Autowired
    TeamService teamService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void saveTeamCreateNewUser() {
        long count = userRepository.count();
        Team team = createValidTeam(TESTED_TEAM_NAME);
        User user = createValidUser();
        addUserToTeam(team, user);
        teamService.save(team);
        Assert.assertEquals(count + 1, userRepository.count());
    }

    @Test
    public void adminUserIsImportedOnStartup() {
        Assert.assertTrue(isUserInDb("admin"));
    }

    @Test
    public void organizerUserIsImportedOnStartup() {
        Assert.assertTrue(isUserInDb("organizer"));
    }

    @Test
    public void updateTeamDoNotCreateNewUser() {
        Team team = createValidTeam(TESTED_TEAM_NAME);
        User user = createValidUser();
        addUserToTeam(team, user);
        teamService.save(team);
        long count = userRepository.count();
        teamService.update(team);
        Assert.assertEquals(count, userRepository.count());
    }

    @Test
    public void deleteTeamRemoveUser() {
        Team team = createValidTeam(TESTED_TEAM_NAME);
        User user = createValidUser();
        addUserToTeam(team, user);
        teamService.save(team);
        long count = userRepository.count();
        teamService.delete(team.getTeamId());
        Assert.assertEquals(count - 1, userRepository.count());
    }

    @Test
    public void addTeamGeneratesProperUsername() {
        Team team = createValidTeam(SPECIAL_TEAM_NAME);
        teamService.save(team);
        Assert.assertTrue(isUserInDb(GENERATED_USERNAME));
    }

    @Test
    public void t1() {
        String newUsername1 = Normalizer
                .normalize(SPECIAL_TEAM_NAME, Normalizer.Form.NFD);


        String newUsername2 = SPECIAL_TEAM_NAME
                .replaceAll("[^\\p{ASCII}]", "");
    }

    private Team createValidTeam(final String teamName) {
        return new Team(teamName, 5, TEAM_PIN);
    }

    private User createValidUser() {
        User user = new User();
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_PASSWORD);
        return user;
    }

    private boolean isUserInDb(final String username) {
        List<User> users = userRepository.findAll();
        Optional<User> testedUser = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findAny();
        return testedUser.isPresent();
    }

    private void addUserToTeam(Team team, User user) {
        team.setUser(user);
    }
}
