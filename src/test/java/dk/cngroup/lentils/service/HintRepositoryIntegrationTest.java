package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.HintRepository;
import dk.cngroup.lentils.repository.HintTakenRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class HintRepositoryIntegrationTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CypherRepository cypherRepository;

    @Autowired
    private HintRepository hintRepository;

    @Autowired
    private HintTakenRepository hintTakenRepository;

    @Test
    public void findHintsNotTakenByTeamWhileOtherTeamsAlsoTakingHintsTest() {
        Team team1 = teamRepository.save(new Team(1L, "prvni", 4, "1111"));
        Team team2 = teamRepository.save(new Team(2L, "druhy", 4, "2222"));
        Team team3 = teamRepository.save(new Team(3L, "treti", 4, "3333"));

        Cypher cypher1 = cypherRepository.save(new Cypher(1));
        Cypher cypher2 = cypherRepository.save(new Cypher(2));
        Cypher cypher3 = cypherRepository.save(new Cypher(3));

        Hint hint1 = hintRepository.save(new Hint("a", 5, cypher1));
        Hint hint2 = hintRepository.save(new Hint("b", 5, cypher1));
        Hint hint3 = hintRepository.save(new Hint("c", 5, cypher1));

        Hint hint4 = hintRepository.save(new Hint("d", 5, cypher2));
        Hint hint5 = hintRepository.save(new Hint("e", 5, cypher2));
        Hint hint6 = hintRepository.save(new Hint("f", 5, cypher2));

        Hint hint7 = hintRepository.save(new Hint("g", 5, cypher3));
        Hint hint8 = hintRepository.save(new Hint("h", 5, cypher3));
        Hint hint9 = hintRepository.save(new Hint("i", 5, cypher3));

        hintTakenRepository.save(new HintTaken(team1, hint1));

        hintTakenRepository.save(new HintTaken(team1, hint4));
        hintTakenRepository.save(new HintTaken(team1, hint5));

        hintTakenRepository.save(new HintTaken(team1, hint7));
        hintTakenRepository.save(new HintTaken(team1, hint8));
        hintTakenRepository.save(new HintTaken(team1, hint9));

        hintTakenRepository.save(new HintTaken(team2, hint1));
        hintTakenRepository.save(new HintTaken(team2, hint4));
        hintTakenRepository.save(new HintTaken(team2, hint7));

        hintTakenRepository.save(new HintTaken(team3, hint1));

        List<Hint> notTakenHintsCypher1Team1 = hintRepository.findHintsNotTakenByTeam(team1.getTeamId(),
                cypher1.getCypherId());
        List<Hint> notTakenHintsCypher2Team1 = hintRepository.findHintsNotTakenByTeam(team1.getTeamId(),
                cypher2.getCypherId());
        List<Hint> notTakenHintsCypher3Team1 = hintRepository.findHintsNotTakenByTeam(team1.getTeamId(),
                cypher3.getCypherId());

        assertEquals(3, hintRepository.findByCypher(cypher1).size());
        assertEquals(3, hintRepository.findByCypher(cypher2).size());
        assertEquals(3, hintRepository.findByCypher(cypher3).size());

        assertEquals(6, hintTakenRepository.findByTeam(team1).size());
        assertEquals(3, hintTakenRepository.findByTeam(team2).size());
        assertEquals(1, hintTakenRepository.findByTeam(team3).size());

        assertEquals(2, notTakenHintsCypher1Team1.size());
        assertEquals(1, notTakenHintsCypher2Team1.size());
        assertEquals(0, notTakenHintsCypher3Team1.size());

        assertEquals(3, teamRepository.findAll().size());
        assertEquals(3, cypherRepository.findAll().size());
        assertEquals(9, hintRepository.findAll().size());
        assertEquals(10, hintTakenRepository.findAll().size());
        assertEquals(6, hintTakenRepository.findByTeam(team1).size());
    }
}
