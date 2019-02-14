package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Progress;
import dk.cngroup.lentils.entity.ProgressKey;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.CypherStatus;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static dk.cngroup.lentils.service.ObjectGenerator.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class ProgressRepositoryTest {

    @Autowired
    ProgressRepository repository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CypherRepository cypherRepository;

    @Autowired
    ObjectGenerator generator;

    @Test
    public void findAllZeroTest() {

        List<Progress> progressList = repository.findAll();
        assertEquals(0, progressList.size());
    }

    @Test
    public void addTest() {

        Team team = generator.generateTeam();
        teamRepository.save(team);

        Cypher cypher = generator.generateCypher();
        cypherRepository.save(cypher);

        ProgressKey progressKey = new ProgressKey(cypher.getId(), team.getId());
        Progress progress = new Progress(progressKey, team, cypher);

        Progress progress1 = repository.save(progress);

        assertEquals(progress, progress1);
    }

    /*
     *  progress of one team on all stages
     * */
    @Test
    public void getTeamProgressTest() {

        Team team = generator.generateTeam();
        teamRepository.save(team);

        List<Cypher> cyphers = generator.generateCypherList(ObjectGenerator.NUMBER_OF_CYPHERS);
        cypherRepository.saveAll(cyphers);

        for (Cypher cypher : cyphers) {
            ProgressKey progressKey = new ProgressKey(cypher.getId(), team.getId());
            repository.save(new Progress(progressKey, team, cypher));
        }

        assertEquals(ObjectGenerator.NUMBER_OF_CYPHERS, repository.count());
    }

    /*
     * status of one cypher for all teams
     * */
    @Test
    public void getStatusOfCypherTest() {

        List<Team> teams = generator.generateTeamList();
        teamRepository.saveAll(teams);

        Cypher cypher = generator.generateCypher();
        cypherRepository.save(cypher);

        for (Team team : teams) {
            ProgressKey progressKey = new ProgressKey(cypher.getId(), team.getId());
            repository.save(new Progress(progressKey, team, cypher));
        }

        assertEquals(ObjectGenerator.NUMBER_OF_TEAMS, repository.count());
    }

    @Test
    public void findAllTest() {

        fillTable();

        List<Progress> progressList = repository.findAll();

        assertEquals(ObjectGenerator.NUMBER_OF_TEAMS * ObjectGenerator.NUMBER_OF_CYPHERS, progressList.size());
    }

    @Test
    public void updateStatusTest() {
        fillTable();

        Cypher cypher = cypherRepository.findByStage(TESTED_STAGE);
        Team team = teamRepository.findByName(TEAM_NAME + TESTED_TEAM);

        Progress progress = repository.findByTeamAndCypher(team, cypher);

        progress.setCypherStatus(CypherStatus.SOLVED);
        Progress progress1 = repository.save(progress);

        assertEquals(CypherStatus.SOLVED, progress1.getCypherStatus());
    }

    private void fillTable() {
        List<Team> teams = generator.generateTeamList();
        teamRepository.saveAll(teams);

        List<Cypher> cyphers = generator.generateCypherList(ObjectGenerator.NUMBER_OF_CYPHERS);
        cypherRepository.saveAll(cyphers);

        List<Progress> progressList = new LinkedList<>();
        for (Team team : teams) {
            for (Cypher cypher : cyphers) {
                ProgressKey progressKey = new ProgressKey(cypher.getId(), team.getId());
                progressList.add(new Progress(progressKey, team, cypher));
            }
        }
        repository.saveAll(progressList);
    }
}
