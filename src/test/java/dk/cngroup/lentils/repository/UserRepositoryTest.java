package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.ObjectGenerator;
import dk.cngroup.lentils.service.TeamService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class UserRepositoryTest {
    @Autowired
    TeamService teamService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void saveTeamCreateNewUser() {
        long count = userRepository.count();
        Team team = new Team("teamA", 5, "1234");
        User user = new User();
        addValidUserToTeam(team, user);
        teamService.save(team);
        Assert.assertEquals(count + 1, userRepository.count());
    }

    @Test
    public void adminUserIsImportedOnStartup() {
        Assert.assertEquals(1, userRepository.count());
        List<User> users = userRepository.findAll();
        Assert.assertEquals("admin",users.get(0).getUsername());
    }

    @Test
    public void updateTeamDoNotCreateNewUser() {
        Team team = new Team("teamA", 5, "1234");
        User user = new User();
        addValidUserToTeam(team, user);
        teamService.save(team);
        long count = userRepository.count();
        teamService.update(team);
        Assert.assertEquals(count, userRepository.count());
    }

    @Test
    public void deleteTeamRemoveUser() {
        Team team = new Team("teamA", 5, "1234");
        User user = new User();
        addValidUserToTeam(team, user);
        teamService.save(team);
        long count = userRepository.count();
        teamService.delete(team.getTeamId());
        Assert.assertEquals(count - 1, userRepository.count());
    }

    private void addValidUserToTeam(Team team, User user) {
        user.setUsername("testUsername");
        user.setPassword("pwd");
        team.setUser(user);
    }
}
