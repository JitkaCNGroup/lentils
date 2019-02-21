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

import static dk.cngroup.lentils.service.CypherStatus.SOLVED;
import static dk.cngroup.lentils.service.ObjectGenerator.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class StatusRepositoryTest {

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
        Team team = generator.generateTeam();
        teamRepository.save(team);

        Cypher cypher = generator.generateCypher();
        cypherRepository.save(cypher);

        StatusKey statusKey = new StatusKey(cypher.getId(), team.getId());
        Status status = new Status(statusKey, team, cypher);

        Status status1 = repository.save(status);

        assertEquals(status, status1);
    }

    /*
     *  progress of one team on all stages
     * */
    @Test
    public void getTeamsStatusTest() {
        Team team = generator.generateTeam();
        teamRepository.save(team);

        List<Cypher> cyphers = generator.generateCypherList(ObjectGenerator.NUMBER_OF_CYPHERS);
        cypherRepository.saveAll(cyphers);

        for (Cypher cypher : cyphers) {
            StatusKey statusKey = new StatusKey(cypher.getId(), team.getId());
            repository.save(new Status(statusKey, team, cypher));
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
            StatusKey statusKey = new StatusKey(cypher.getId(), team.getId());
            repository.save(new Status(statusKey, team, cypher));
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
                StatusKey statusKey = new StatusKey(cypher.getId(), team.getId());
                statusList.add(new Status(statusKey, team, cypher, CypherStatus.SOLVED));
            }
        }
        repository.saveAll(statusList);
    }
}
