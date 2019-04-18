package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.TeamRepository;
import dk.cngroup.lentils.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class TeamIntegrationTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addingOneTeamCorrectlyCreatesUser() {
        createAndSaveTeam("pump", 5, "8888");
        assertEquals(1, teamService.getAll().size());
        assertEquals(3, userRepository.findAll().size());
        assertNotEquals(userRepository.findByUsername("pump").get().getUserId(),
                teamRepository.findByName("pump").getTeamId());
    }

    @Test
    public void addingFourTeamsCorrectlyCreatesUsers() {
        createAndSaveTeam("a", 5, "1111");
        createAndSaveTeam("b", 5, "2222");
        createAndSaveTeam("c", 5, "3333");
        createAndSaveTeam("d", 5, "4444");
        assertEquals(4, teamService.getAll().size());
        assertEquals(6, userRepository.findAll().size());
        assertNotEquals(userRepository.findByUsername("a").get().getUserId(),
                teamRepository.findByName("a").getTeamId());
        assertNotEquals(userRepository.findByUsername("b").get().getUserId(),
                teamRepository.findByName("b").getTeamId());
        assertNotEquals(userRepository.findByUsername("c").get().getUserId(),
                teamRepository.findByName("c").getTeamId());
        assertNotEquals(userRepository.findByUsername("d").get().getUserId(),
                teamRepository.findByName("d").getTeamId());
    }

    @Test
    public void noTeamUserCheck() {
        assertEquals(2, userRepository.findAll().size());
    }

    private void createAndSaveTeam(String name, int numberOfMembers, String pin) {
        Team team = new Team(name, numberOfMembers, pin);
        teamService.save(team);
    }
}