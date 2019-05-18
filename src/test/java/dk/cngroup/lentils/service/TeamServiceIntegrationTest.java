package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.HintRepository;
import dk.cngroup.lentils.repository.HintTakenRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import dk.cngroup.lentils.repository.UserRepository;
import dk.cngroup.lentils.util.UsernameUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class})
public class TeamServiceIntegrationTest {

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CypherRepository cypherRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private HintRepository hintRepository;
    @Autowired
    private HintTakenRepository hintTakenRepository;
    @Autowired
    private ObjectGenerator generator;

    private int numOfUsersBeforeTest;
    private int numOfTeamsBeforeTest;

    @Before
    public void setup() {
        createVariousTeams();
        numOfUsersBeforeTest = userRepository.findAll().size();
        numOfTeamsBeforeTest = teamRepository.findAll().size();
    }

    @Test
    public void addingOneTeamCorrectlyCreatesUser() {
        createAndSaveTeam("pump", "8888");

        Assert.assertEquals(numOfTeamsBeforeTest + 1, teamRepository.count());
        Assert.assertEquals(numOfUsersBeforeTest + 1, userRepository.count());
        assertThatTeamHasAssociatedCorrectUser("pump");
    }

    @Test
    public void addingFourTeamsCorrectlyCreatesUsers() {
        createAndSaveTeam("a", "1111");
        createAndSaveTeam("b","2222");
        createAndSaveTeam("c","3333");
        createAndSaveTeam("d","4444");

        Assert.assertEquals(numOfTeamsBeforeTest + 4, teamService.getAll().size());
        Assert.assertEquals(numOfUsersBeforeTest + 4, userRepository.findAll().size());
        assertThatTeamHasAssociatedCorrectUser("a");
        assertThatTeamHasAssociatedCorrectUser("b");
        assertThatTeamHasAssociatedCorrectUser("c");
        assertThatTeamHasAssociatedCorrectUser("d");
    }

    @Test
    public void searchTeamOneResultTest(){
        List<Team> teams = teamService.searchTeams("Sparta");

        Assert.assertEquals(1, teams.size());
    }

    @Test
    public void searchTeamMultipleResultsTest() {
        List<Team> teams = teamService.searchTeams("Sevci");

        Assert.assertEquals(2, teams.size());
    }

    @Test
    public void searchTeamNoResultTest() {
        List<Team> teams = teamService.searchTeams("Pribram");

        Assert.assertEquals(0, teams.size());
    }

    @Test
    public void teamNameIsNotUniqueTest() {
        Assert.assertFalse(teamService.isTeamNameUnique("Sparta"));
    }

    @Test
    public void teamNameIsUniqueTest() {
        Assert.assertTrue(teamService.isTeamNameUnique("Dragons"));
    }

    @Test
    public void testDeleteTeam() {
        final Cypher cypher = generator.generateValidCypher();
        cypherRepository.save(cypher);
        final Team team = createAndSaveTeam("alpha", "1111");
        final List<Hint> hints = generator.generateHintsForCypher(cypher);
        hintRepository.saveAll(hints);

        // Create status for team
        final Status status = new Status();
        status.setTeam(team);
        status.setCypher(cypher);
        status.setCypherStatus(CypherStatus.PENDING);
        statusRepository.saveAndFlush(status);

        // Create one taken hint
        final HintTaken hintTaken = new HintTaken();
        hintTaken.setTeam(team);
        hintTaken.setHint(hints.get(0));
        hintTakenRepository.saveAndFlush(hintTaken);

        final long countBefore = teamRepository.count();

        teamService.delete(team.getTeamId());

        Assert.assertEquals(countBefore - 1, teamRepository.count());
    }

    private void createVariousTeams() {
        createAndSaveTeam("Sparta","0123");
        createAndSaveTeam("Banik","4567");
        createAndSaveTeam("SevciA","8901");
        createAndSaveTeam("SevciB","2345");
    }

    private Team createAndSaveTeam(String name, String pin) {
        Team team = generator.generateTeamWithNameAndPin(name, pin);
        return teamService.save(team);
    }

    private void assertThatTeamHasAssociatedCorrectUser(final String teamName) {
        final Team team = teamRepository.findByName(teamName);
        final User user = userRepository
                .findByUsername(UsernameUtils.generateUsername(teamName))
                .orElseThrow(() -> new RuntimeException("Cannot find user"));

        Assert.assertNotNull(team);
        Assert.assertEquals(team.getUser().getUserId(), user.getUserId());
        Assert.assertEquals(user.getTeam().getTeamId(), team.getTeamId());
    }
}