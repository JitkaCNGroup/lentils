package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class StatusServiceIntegrationTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CypherRepository cypherRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusService statusService;

    private ObjectGenerator objectGenerator = new ObjectGenerator();

    @Test
    public void initializeStatusForTeamAndCypher() {
        Team team = objectGenerator.generateValidTeam();
        teamRepository.save(team);
        Cypher cypher = objectGenerator.generateNewCypher();
        cypherRepository.save(cypher);
        statusService.initializeStatusForTeamAndCypher(cypher, team);

        assertEquals(CypherStatus.LOCKED, statusService.getCypherStatusByTeamAndCypher(team, cypher));
    }

    @Test
    public void markCypherWhenNextCyphersAreLocked() {
        Team team = objectGenerator.generateValidTeam();
        teamRepository.saveAndFlush(team);
        final Cypher cypher1 = getCypherWithStageNumber(101);
        setStatusForCypherAndTeam(cypher1, team, CypherStatus.PENDING);
        final Cypher cypher2 = getCypherWithStageNumber(102);
        setStatusForCypherAndTeam(cypher2, team, CypherStatus.LOCKED);
        final Cypher cypher3 = getCypherWithStageNumber(103);
        setStatusForCypherAndTeam(cypher3, team, CypherStatus.LOCKED);

        statusService.markCypher(cypher1, team, CypherStatus.SOLVED);

        assertEquals(CypherStatus.PENDING, statusService.getCypherStatusByTeamAndCypher(team, cypher2));
        assertEquals(CypherStatus.SOLVED, statusService.getCypherStatusByTeamAndCypher(team, cypher1));
    }

    @Test
    public void markCypherWhenNextCyphersAreNotLocked() {
        Team team = objectGenerator.generateValidTeam();
        teamRepository.saveAndFlush(team);
        final Cypher cypher1 = getCypherWithStageNumber(101);
        setStatusForCypherAndTeam(cypher1, team, CypherStatus.SOLVED);
        final Cypher cypher2 = getCypherWithStageNumber(102);
        setStatusForCypherAndTeam(cypher2, team, CypherStatus.SOLVED);
        final Cypher cypher3 = getCypherWithStageNumber(103);
        setStatusForCypherAndTeam(cypher3, team, CypherStatus.PENDING);

        statusService.markCypher(cypher1, team, CypherStatus.PENDING);

        assertEquals(CypherStatus.SOLVED, statusService.getCypherStatusByTeamAndCypher(team, cypher2));
        assertEquals(CypherStatus.PENDING, statusService.getCypherStatusByTeamAndCypher(team, cypher1));
    }

    @Test
    public void markLastCypher() {
        Team team = objectGenerator.generateValidTeam();
        teamRepository.saveAndFlush(team);
        final Cypher cypher1 = getCypherWithStageNumber(101);
        setStatusForCypherAndTeam(cypher1, team, CypherStatus.SOLVED);
        final Cypher cypher2 = getCypherWithStageNumber(102);
        setStatusForCypherAndTeam(cypher2, team, CypherStatus.SOLVED);
        final Cypher cypher3 = getCypherWithStageNumber(103);
        setStatusForCypherAndTeam(cypher3, team, CypherStatus.SOLVED);

        statusService.markCypher(cypher3, team, CypherStatus.PENDING);

        assertEquals(CypherStatus.PENDING, statusService.getCypherStatusByTeamAndCypher(team, cypher3));
    }

    private Cypher getCypherWithStageNumber(final int stageNumber) {
        final Cypher cypher = objectGenerator.generateValidCypher();
        cypher.setStage(stageNumber);
        return cypherRepository.save(cypher);
    }

    private Status setStatusForCypherAndTeam(final Cypher cypher, final Team team, final CypherStatus cypherStatus) {
        final Status status = new Status();
        status.setCypherStatus(cypherStatus);
        status.setCypher(cypher);
        status.setTeam(team);
        return statusRepository.saveAndFlush(status);
    }
}
