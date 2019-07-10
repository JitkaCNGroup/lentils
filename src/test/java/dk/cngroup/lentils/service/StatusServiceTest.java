package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.NextCypherNotFoundException;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static dk.cngroup.lentils.service.ObjectGenerator.NUMBER_OF_CYPHERS;
import static dk.cngroup.lentils.service.ObjectGenerator.NUMBER_OF_TEAMS;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StatusServiceTest {

    @InjectMocks
    StatusService service;

    @Mock
    CypherRepository cypherRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    StatusRepository statusRepository;

    @Mock
    CypherService cypherService;

    private ObjectGenerator generator = new ObjectGenerator();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service.setCypherService(cypherService);
    }

    @Test
    public void getStatusOfAllTeamsTest() {

        List<Status> statusList = fillTeamCypherAndStatusTables();
        when(statusRepository.findAll()).thenReturn(statusList);

        assertEquals(NUMBER_OF_CYPHERS * NUMBER_OF_TEAMS, service.getAll().size());
    }

    @Test
    public void getAllTest() {
        final List<Status> testCollection = Collections.singletonList(new Status());
        when(statusRepository.findAll()).thenReturn(testCollection);

        final List<Status> returnedList = service.getAll();

        assertEquals(testCollection, returnedList);
    }

    @Test
    public void markCypherTest() {
        final Status statusEntity = new Status();
        statusEntity.setCypherStatus(CypherStatus.PENDING);
        when(statusRepository.findByTeamAndCypher(any(), any())).thenReturn(statusEntity);
        when(cypherService.getNext(any())).thenThrow(new NextCypherNotFoundException());

        service.markCypher(new Cypher(1), new Team(), CypherStatus.SOLVED);

        assertEquals(CypherStatus.SOLVED, statusEntity.getCypherStatus());
        verify(statusRepository).save(statusEntity);
    }

    @Test(expected = IllegalStateException.class)
    public void markCypherTestWithInvalidArguments() {
        final Status statusEntity = new Status();
        statusEntity.setCypherStatus(CypherStatus.PENDING);
        when(statusRepository.findByTeamAndCypher(any(), any())).thenReturn(null);

        service.markCypher(new Cypher(), new Team(), CypherStatus.SOLVED);
    }

    @Test
    public void getStatusScoreTest_withLockedStatus() {
        verifyGetStatusScore(CypherStatus.LOCKED);
    }

    @Test
    public void getStatusScoreTest_withSkippedStatus() {
        verifyGetStatusScore(CypherStatus.SKIPPED);
    }

    @Test
    public void getStatusScoreWithInvalidParameters() {
        when(statusRepository.findByTeamAndCypher(any(), any())).thenReturn(null);

       assertEquals(0, service.getStatusScore(new Team(), new Cypher()));
    }

    @Test
    public void getAllByCypherTest() {
        final Cypher parameter = new Cypher();
        final List<Status> expectedList = Collections.singletonList(new Status());
        when(statusRepository.findAllByCypher(eq(parameter))).thenReturn(expectedList);

        final List<Status> returnedList = service.getAllByCypher(parameter);

        assertEquals(expectedList, returnedList);
        verify(statusRepository).findAllByCypher(eq(parameter));
    }

    private void verifyGetStatusScore(CypherStatus status) {
        final Status statusEntity = new Status();
        statusEntity.setCypherStatus(status);

        when(statusRepository.findByTeamAndCypher(any(), any())).thenReturn(statusEntity);

        final int returnedValue = service.getStatusScore(new Team(), new Cypher());

        assertEquals(status.getStatusValue(), returnedValue);
    }

    @Test
    public void getStatusScoreForOneSolvedCyper() {
        Status status = generateOneTeamCypherAndStatusRow("SOLVED");
        when(statusRepository.findByTeamAndCypher(any(), any())).thenReturn(status);

        assertEquals(10, service.getStatusScore(status.getTeam(), status.getCypher()));
    }

    @Test
    public void getStatusScoreForOneSkippedCyper() {
        Status status = generateOneTeamCypherAndStatusRow("SKIPPED");
        when(statusRepository.findByTeamAndCypher(any(), any())).thenReturn(status);

        assertEquals(0, service.getStatusScore(status.getTeam(), status.getCypher()));
    }

    @Test
    public void getStatusScoreForOnePendingCyper() {
        Status status = generateOneTeamCypherAndStatusRow("PENDING");
        when(statusRepository.findByTeamAndCypher(any(), any())).thenReturn(status);

        assertEquals(0, service.getStatusScore(status.getTeam(), status.getCypher()));
    }

    @Test
    public void getStatusScoreForOneLockedCyper() {
        Status status = generateOneTeamCypherAndStatusRow("LOCKED");
        when(statusRepository.findByTeamAndCypher(any(), any())).thenReturn(status);

        assertEquals(0, service.getStatusScore(status.getTeam(), status.getCypher()));
    }

    @Test
    public void restOfCyphersAreLockedIsTrueTest() {
        List<Cypher> cyphers = new LinkedList<>();
        Cypher cypher = generator.generateNewCypher();
        cypher.setStage(1);
        cyphers.add(cypher);
        Team team = generator.generateValidTeam();
        when (cypherRepository.findByStageGreaterThanOrderByStageAsc(1)).thenReturn(cyphers);
        when (statusRepository.existsStatusByCypherAndTeamAndCypherStatus(any(), any(), any()))
                .thenReturn(true);

        assertEquals(true, service.restOfCyphersAreLocked (team, cypher));
    }

    @Test
    public void restOfCyphersAreLockedIsFalseTest() {
        List<Cypher> cyphers = new LinkedList<>();
        Cypher cypher = generator.generateNewCypher();
        cypher.setStage(1);
        cyphers.add(cypher);
        Team team = generator.generateValidTeam();
        when (cypherRepository.findByStageGreaterThanOrderByStageAsc(1)).thenReturn(cyphers);
        when (statusRepository.existsStatusByCypherAndTeamAndCypherStatus(any(), any(), any()))
                .thenReturn(false);

        assertEquals(false, service.restOfCyphersAreLocked (team, cypher));
    }

    private List<Status> fillTeamCypherAndStatusTables() {
        List<Team> teams = generator.generateTeamList();
        when(teamRepository.saveAll(teams)).thenReturn(teams);

        List<Cypher> cyphers = generator.generateCypherList(NUMBER_OF_CYPHERS);
        when(cypherRepository.saveAll(cyphers)).thenReturn(cyphers);

        List<Status> statusList = new LinkedList<>();
        for (Team team : teams) {
            for (Cypher cypher : cyphers) {
                statusList.add(new Status(team, cypher, CypherStatus.SOLVED));
            }
        }

        when(statusRepository.saveAll(statusList)).thenReturn(statusList);
        return statusList;
    }

    private Status generateOneTeamCypherAndStatusRow(String valueOfStatus) {
        Team team = generator.generateValidTeam();
        Cypher cypher = generator.generateNewCypher();
        Status status = new Status(team, cypher, CypherStatus.valueOf(valueOfStatus));

        return status;
    }

    private Status getStatusFromList(String nameOfTestedTeam, int testedStage, List<Status> statusList) {
        for (Status status : statusList) {
            if (status.getCypher().getStage() == testedStage
                    && status.getTeam().getName().equals(nameOfTestedTeam)) {
                return status;
            }
        }
        return null;
    }

}
