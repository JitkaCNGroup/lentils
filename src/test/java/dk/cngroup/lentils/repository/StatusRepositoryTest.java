package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static dk.cngroup.lentils.entity.CypherStatus.*;
import static dk.cngroup.lentils.service.ObjectGenerator.TEAM_NAME;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class StatusRepositoryTest {
    private static final int TESTED_STAGE = 5;
    private static final int TESTED_TEAM = 5;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CypherRepository cypherRepository;

    @Autowired
    ObjectGenerator generator;

    @Test
    public void findAllZeroTest() {
        List<Status> statusList = statusRepository.findAll();

        assertEquals(0, statusList.size());
    }

    @Test
    public void addTest() {
        Team team = teamRepository.save(new Team(TEAM_NAME + TESTED_TEAM, 5, "1234"));
        Cypher cypher = cypherRepository.save(generator.generateValidCypher());
        Status status = statusRepository.save(new Status(team, cypher, PENDING));

        assertEquals(1, statusRepository.count());
    }

    /*
     * status of one cypher for all teams
     * */
    @Test
    public void getStatusOfCypherTest() {
        List<Team> teams = generator.generateTeamList();
        teamRepository.saveAll(teams);

        Cypher cypher = generator.generateValidCypher();
        Cypher cypherSaved =  cypherRepository.save(cypher);

        for (Team team : teams) {
            statusRepository.save(new Status(team, cypherSaved, PENDING));
        }

        assertEquals(ObjectGenerator.NUMBER_OF_TEAMS, statusRepository.count());
    }

    @Test
    public void findAllTest() {
        fillTable();
        List<Status> statusList = statusRepository.findAll();

        assertEquals(ObjectGenerator.NUMBER_OF_TEAMS * ObjectGenerator.NUMBER_OF_CYPHERS, statusList.size());
    }

    @Test
    public void updateStatusTest() {
        fillTable();

        Cypher cypher = cypherRepository.findByStage(TESTED_STAGE);
        Team team = teamRepository.findByName(TEAM_NAME + TESTED_TEAM);

        Status status = statusRepository.findByTeamAndCypher(team, cypher);

        status.setCypherStatus(SOLVED);
        Status status1 = statusRepository.save(status);

        assertEquals(SOLVED, status1.getCypherStatus());
    }

    @Test
    public void findStatusesBasedOnCypher() {
        Cypher cypher = createTestData();

        Specification<Status> hasCypherSpec = StatusRepositorySpec.hasCypher(cypher);

        assertEquals(3, statusRepository.findAll(hasCypherSpec).size());
    }

    @Test
    public void findStatusesBasedOnTeamName() {
        createTestData();

        Specification<Status> hasTeamNameSpec = StatusRepositorySpec.hasTeamName("aaa");

        assertEquals(6, statusRepository.findAll(hasTeamNameSpec).size());
    }

    @Test
    public void findStatusesBasedOnCypherStatus() {
        createTestData();

        Specification<Status> hasCypherStatusSpec = StatusRepositorySpec.hasCypherStatus(PENDING);

        assertEquals(3, statusRepository.findAll(hasCypherStatusSpec).size());
    }

    @Test
    public void findStatusesBasedOnNotHaveCypherStatus() {
        createTestData();

        Specification<Status> hasNotCypherStatusSpec = StatusRepositorySpec.hasNotCypherStatus(PENDING);

        assertEquals(6, statusRepository.findAll(hasNotCypherStatusSpec).size());
    }

    @Test
    public void findStatusesBasedOnTeamNameAndCypher() {
        Cypher cypher = createTestData();

        Specification<Status> hasTeamNameSpec = StatusRepositorySpec.hasTeamName("aaa");
        Specification<Status> hasCypherSpec = StatusRepositorySpec.hasCypher(cypher);

        assertEquals(2, statusRepository.findAll(Specification.where(hasTeamNameSpec).and(hasCypherSpec)).size());
    }

    @Test
    public void findStatusesBasedOnTeamNameAndCypherStatus() {
        createTestData();

        Specification<Status> hasTeamNameSpec = StatusRepositorySpec.hasTeamName("aaa");
        Specification<Status> hasCypherStatusSpec = StatusRepositorySpec.hasCypherStatus(PENDING);

        assertEquals(2, statusRepository.findAll(Specification.where(hasTeamNameSpec).and(hasCypherStatusSpec)).size());
    }

    @Test
    public void findStatusesBasedOnCypherAndCypherStatus() {
        Cypher cypher = createTestData();

        Specification<Status> hasCypherSpec = StatusRepositorySpec.hasCypher(cypher);
        Specification<Status> hasCypherStatusSpec = StatusRepositorySpec.hasCypherStatus(PENDING);

        assertEquals(1, statusRepository.findAll(Specification.where(hasCypherSpec).and(hasCypherStatusSpec)).size());
    }

    @Test
    public void findStatusesBasedOnTeamNameAndCypherAndCypherStatus() {
        Cypher cypher = createTestData();

        Specification<Status> hasTeamNameSpec = StatusRepositorySpec.hasTeamName("aaa");
        Specification<Status> hasCypherSpec = StatusRepositorySpec.hasCypher(cypher);
        Specification<Status> hasCypherStatusSpec = StatusRepositorySpec.hasCypherStatus(LOCKED);

        assertEquals(1, statusRepository.findAll(Specification.where(hasTeamNameSpec).and(hasCypherSpec).and(hasCypherStatusSpec)).size());
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
        statusRepository.saveAll(statusList);
    }

    private Cypher createTestData() {
        Team team1 = teamRepository.save(generator.generateTeamWithNameAndPin("aaa", "1234"));
        Team team2 = teamRepository.save(generator.generateTeamWithNameAndPin("bbb", "5678"));
        Team team3 = teamRepository.save(generator.generateTeamWithNameAndPin("AAABBB", "9876"));

        Cypher cypher1 = cypherRepository.save(generator.generateValidCypher());
        Cypher cypher2 = cypherRepository.save(generator.generateValidCypher());
        Cypher cypher3 = cypherRepository.save(generator.generateValidCypher());

        statusRepository.save(new Status(team1, cypher1, PENDING));
        statusRepository.save(new Status(team1, cypher2, SOLVED));
        statusRepository.save(new Status(team1, cypher3, SKIPPED));
        statusRepository.save(new Status(team2, cypher1, LOCKED));
        statusRepository.save(new Status(team2, cypher2, PENDING));
        statusRepository.save(new Status(team2, cypher3, SKIPPED));
        statusRepository.save(new Status(team3, cypher1, LOCKED));
        statusRepository.save(new Status(team3, cypher2, PENDING));
        statusRepository.save(new Status(team3, cypher3, SKIPPED));

        return cypher1;
    }
}
