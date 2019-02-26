package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.StatusKey;
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

import static dk.cngroup.lentils.service.CypherStatus.PENDING;
import static dk.cngroup.lentils.service.CypherStatus.SOLVED;
import static dk.cngroup.lentils.service.ObjectGenerator.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class StatusRepositoryTest {
    private static final int TESTED_STAGE = 3;
    private static final int TESTED_TEAM = 5;

    @Autowired
    StatusRepository repository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CypherRepository cypherRepository;

    @Autowired
    ObjectGenerator generator;

    @Test
    public void findAllZeroTest() {
        List<Status> statusList = repository.findAll();

        assertEquals(0, statusList.size());
    }

    @Test
    public void addTest() {
        Team team = teamRepository.save(new Team(TEAM_NAME + TESTED_TEAM, 5, "1234"));
        Cypher cypher = cypherRepository.save(new Cypher(TESTED_STAGE));
        Status status = repository.save(new Status(team, cypher, PENDING));

        assertEquals(1, repository.count());
    }

    /*
     * status of one cypher for all teams
     * */
    @Test
    public void getStatusOfCypherTest() {

        List<Team> teams = generator.generateTeamList();
        teamRepository.saveAll(teams);

        Cypher cypher = new Cypher(3);
        Cypher cypherSaved =  cypherRepository.save(cypher);

        for (Team team : teams) {
            repository.save(new Status(team, cypherSaved, PENDING));
        }

        assertEquals(ObjectGenerator.NUMBER_OF_TEAMS, repository.count());
    }

    @Test
    public void findAllTest() {
        fillTable();
        List<Status> statusList = repository.findAll();

        assertEquals(ObjectGenerator.NUMBER_OF_TEAMS * ObjectGenerator.NUMBER_OF_CYPHERS, statusList.size());
    }

    @Test
    public void updateStatusTest() {
        fillTable();

        Cypher cypher = cypherRepository.findByStage(TESTED_STAGE);
        Team team = teamRepository.findByName(TEAM_NAME + TESTED_TEAM);

        Status status = repository.findByTeamAndCypher(team, cypher);

        status.setCypherStatus(SOLVED);
        Status status1 = repository.save(status);

        assertEquals(SOLVED, status1.getCypherStatus());
    }

    private void fillTable() {
        List<Team> teams = generator.generateTeamList();
        teamRepository.saveAll(teams);

        List<Cypher> cyphers = generator.generateCypherList(ObjectGenerator.NUMBER_OF_CYPHERS);
        cypherRepository.saveAll(cyphers);

        List<Status> statusList = new LinkedList<>();
        for (Team team : teams) {
            for (Cypher cypher : cyphers) {
                statusList.add(new Status(team, cypher, CypherStatus.SOLVED));
            }
        }
        repository.saveAll(statusList);
    }
}
