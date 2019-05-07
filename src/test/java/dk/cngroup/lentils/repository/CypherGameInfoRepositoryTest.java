package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.entity.view.CypherGameInfo;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class CypherGameInfoRepositoryTest {
    @Autowired
    private CypherGameInfoRepository cypherGameInfoRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private CypherRepository cypherRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private HintRepository hintRepository;
    @Autowired
    private HintTakenRepository hintTakenRepository;

    private ObjectGenerator objectGenerator = new ObjectGenerator();

    private Cypher cypher1;
    private Cypher cypher2;
    private Team team1;
    private Team team2;
    private Hint hint1;
    private Hint hint2;
    private Hint hint3;

    @Before
    public void setup() {
        cypher1 = generateCypher(1);
        cypher2 = generateCypher(2);

        team1 = generateTeam("teamA", "1234");
        team2 = generateTeam("teamB", "4567");
    }

    @Test
    public void testThatEveryCypherAndTeamCombinationIsQueried_NoStatutesSet() {
        List<CypherGameInfo> result = cypherGameInfoRepository.findAll();

        assertEquals(4, result.size());
    }

    @Test
    public void testThatEveryCypherAndTeamCombinationIsQueried_AllStatutesSet() {
        setupCypherStatusEntries();
        List<CypherGameInfo> result = cypherGameInfoRepository.findAll();

        assertEquals(4, result.size());
    }

    @Test
    public void testThatEveryCypherAndTeamCombinationIsQueried_AllStatutesAndHintsSet() {
        setupCypherStatusEntries();
        setupHints();
        List<CypherGameInfo> result = cypherGameInfoRepository.findAll();

        assertEquals(4, result.size());
    }

    @Test
    public void testThatNoHintCountIsReturned_NoStatusesSet() {
        List<CypherGameInfo> result = cypherGameInfoRepository.findAll();

        for (CypherGameInfo info : result) {
            assertEquals(0, info.getCount());
        }
    }

    @Test
    public void testThatNoHintCountIsReturned_AllStatusesSet() {
        setupCypherStatusEntries();
        List<CypherGameInfo> result = cypherGameInfoRepository.findAll();

        for (CypherGameInfo info : result) {
            assertEquals(0, info.getCount());
        }
    }

    @Test
    public void testThatNoHintCountIsReturned_AllStatusesAndHintsSet() {
        setupCypherStatusEntries();
        setupHints();
        List<CypherGameInfo> result = cypherGameInfoRepository.findAll();

        for (CypherGameInfo info : result) {
            assertEquals(0, info.getCount());
        }
    }

    @Test
    public void testTeamWithHintTaken() {
        setupCypherStatusEntries();
        setupHints();
        setupTakenHint(team1, hint1);

        List<CypherGameInfo> result1 = cypherGameInfoRepository.findAllByTeamId(team1.getTeamId());
        List<CypherGameInfo> result2 = cypherGameInfoRepository.findAllByTeamId(team2.getTeamId());

        assertEquals(2, result1.size());
        assertEquals(2, result2.size());

        int sum1 = result1.stream()
                .mapToInt(CypherGameInfo::getCount)
                .sum();
        int sum2 = result2.stream()
                .mapToInt(CypherGameInfo::getCount)
                .sum();

        assertEquals(1, sum1);
        assertEquals(0, sum2);
    }

    @Test
    public void testTeamWithHintTaken_otherHasNotStartedTheGame() {
        saveStatus(cypher1, team1);
        saveStatus(cypher2, team1);
        setupHints();
        setupTakenHint(team1, hint1);
        List<CypherGameInfo> result1 = cypherGameInfoRepository.findAllByTeamId(team1.getTeamId());
        List<CypherGameInfo> result2 = cypherGameInfoRepository.findAllByTeamId(team2.getTeamId());

        assertEquals(2, result1.size());
        assertEquals(2, result2.size());

        int sum1 = result1.stream()
                .mapToInt(CypherGameInfo::getCount)
                .sum();
        int sum2 = result2.stream()
                .mapToInt(CypherGameInfo::getCount)
                .sum();

        assertEquals(1, sum1);
        assertEquals(0, sum2);
    }

    private Cypher generateCypher(final int stage) {
        Cypher cypher = new Cypher();

        cypher.setStage(stage);
        cypher.setLocation(new Point(0, 0));
        cypher.setCodeword("dummy");
        cypher.setMapAddress("https://abc.com");

        cypherRepository.saveAndFlush(cypher);

        return cypher;
    }

    private Team generateTeam(final String name, final String pin) {
        Team team = new Team();
        User user = objectGenerator.createUser("user_" + name);

        team.setName(name);
        team.setNumOfMembers(4);
        team.setPin(pin);
        team.setUser(user);

        teamRepository.saveAndFlush(team);

        return team;
    }

    private void setupCypherStatusEntries() {
        saveStatus(cypher1, team1);
        saveStatus(cypher1, team2);
        saveStatus(cypher2, team1);
        saveStatus(cypher2, team2);
    }

    private void saveStatus(final Cypher cypher, final Team team) {
        Status status = new Status();

        status.setCypher(cypher);
        status.setTeam(team);
        status.setCypherStatus(CypherStatus.PENDING);

        statusRepository.saveAndFlush(status);
    }

    private void setupTakenHint(final Team team, final Hint hint) {
        final HintTaken hintTaken = new HintTaken();

        hintTaken.setTeam(team);
        hintTaken.setHint(hint);

        hintTakenRepository.saveAndFlush(hintTaken);
    }

    private void setupHints() {
        hint1 = saveHint(cypher1);
        hint2 = saveHint(cypher1);
        hint3 = saveHint(cypher2);
    }

    private Hint saveHint(final Cypher cypher) {
        final Hint hint = new Hint();

        hint.setValue(5);
        hint.setText("lorem ipsum");
        hint.setCypher(cypher);

        return hintRepository.saveAndFlush(hint);
    }
}
