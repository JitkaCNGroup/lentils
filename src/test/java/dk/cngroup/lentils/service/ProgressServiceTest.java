package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.HintTaken;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.factory.TeamProgressFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProgressServiceTest {
    @Mock
    private StatusService statusService;
    @Mock
    private HintTakenService hintTakenService;
    @Mock
    private TeamService teamService;

    private ObjectGenerator generator = new ObjectGenerator();

    @InjectMocks
    private ProgressService progressService;

    @Test
    public void testGetTeamsStatuses_emptyData() {
        when(statusService.getAllByCypher(any())).thenReturn(Collections.emptyList());

        Map<Long, CypherStatus> result = progressService.getTeamsStatuses(null);

        assertTrue(result.isEmpty());
    }

    /**
     * Check that method returns more or less correct mapping table.
     */
    @Test
    public void testGetTeamsStatuses() {
        final Team team1 = new Team();
        final Team team2 = new Team();
        team1.setTeamId(1L);
        team2.setTeamId(2L);

        final Cypher cypher = new Cypher();
        cypher.setCypherId(10L);

        List<Status> dataset = new ArrayList<>();
        addStatusIntoList(dataset, cypher, team1, CypherStatus.PENDING);
        addStatusIntoList(dataset, cypher, team2, CypherStatus.SOLVED);

        when(statusService.getAllByCypher(eq(cypher))).thenReturn(dataset);

        Map<Long, CypherStatus> result = progressService.getTeamsStatuses(cypher);

        assertEquals(2, result.size());
        assertEquals(result.get(team1.getTeamId()), CypherStatus.PENDING);
        assertEquals(result.get(team2.getTeamId()), CypherStatus.SOLVED);

    }

    @Test
    public void testSetTakenHintsToMap_cypherHasNoHints() {
        final Cypher cypher = new Cypher();
        cypher.setHints(Collections.emptyList());

        when(hintTakenService.getTakenHintsOfTeam(any())).thenReturn(Collections.emptyList());

        Map<Long, String> result = progressService.setTakenHintsToMap(cypher, new Team());

        assertTrue(result.isEmpty());
    }

    @Test
    public void testSetTakenHintsToMap_noTakenHints() {
        final Cypher cypher = createCypherWithHints();

        when(hintTakenService.getTakenHintsOfTeam(any())).thenReturn(Collections.emptyList());

        Map<Long, String> result = progressService.setTakenHintsToMap(cypher, new Team());

        assertTrue(result.isEmpty());

    }

    @Test
    public void testSetTakenHints() {
        final Cypher cypher = createCypherWithHints();
        final Team team = new Team();
        final HintTaken hintTaken = new HintTaken();
        hintTaken.setHint(cypher.getHints().get(0));
        hintTaken.setTeam(team);

        when(hintTakenService.getTakenHintsOfTeam(eq(team))).thenReturn(Collections.singletonList(hintTaken));

        Map<Long, String> result = progressService.setTakenHintsToMap(cypher, team);

        assertFalse(result.isEmpty());
        assertTrue(result.containsKey(cypher.getHints().get(0).getHintId()));
        assertFalse(result.containsKey(cypher.getHints().get(1).getHintId()));
        assertEquals(cypher.getHints().get(0).getText(), result.get(cypher.getHints().get(0).getHintId()));
    }

    @Test
    public void testGetCyphersStatuses() {
        final Team team = new Team();
        team.setTeamId(1L);

        final Cypher cypher1 = new Cypher();
        final Cypher cypher2 = new Cypher();
        cypher1.setCypherId(10L);
        cypher2.setCypherId(20L);

        List<Status> dataset = new ArrayList<>();
        addStatusIntoList(dataset, cypher1, team, CypherStatus.PENDING);
        addStatusIntoList(dataset, cypher2, team, CypherStatus.SOLVED);

        when(statusService.getAllByTeam(eq(team))).thenReturn(dataset);

        Map<Long, CypherStatus> result = progressService.getCyphersStatuses(team);

        assertEquals(2, result.size());
        assertEquals(result.get(cypher1.getCypherId()), CypherStatus.PENDING);
        assertEquals(result.get(cypher2.getCypherId()), CypherStatus.SOLVED);
    }

    @Test
    public void testGetCypherStatuses_emptyData() {
        when(statusService.getAllByTeam(any())).thenReturn(Collections.emptyList());

        Map<Long, CypherStatus> result = progressService.getCyphersStatuses(null);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetTeamProgressByPlayingTeam() {
        Team team = generator.generateValidTeam();
        Cypher cypher = generator.generateValidCypherWithStage(1);
        Status status = new Status(team, cypher, CypherStatus.PENDING);
        List<Status> statusList = new LinkedList<>();
        statusList.add(status);
        when(statusService.getPendingCyphers(team)).thenReturn(statusList);
        when(statusService.getAllByTeam(any())).thenReturn(statusList);

        assertEquals("Aktuální stage: 1", TeamProgressFactory.create(team, statusService).toString());
    }

    @Test
    public void testGetTeamProgressByNotStartedTeam() {
        Team team = generator.generateValidTeam();
        Cypher cypher = generator.generateValidCypherWithStage(1);
        List<Status> statusList = new LinkedList<>();
        when(statusService.getAllByTeam(any())).thenReturn(statusList);

        assertEquals("Hra nezahájena", TeamProgressFactory.create(team, statusService).toString());
    }

    @Test
    public void testGetTeamProgressByFinishedTeam() {
        Team team = generator.generateValidTeam();
        Cypher cypher = generator.generateValidCypherWithStage(1);
        List<Status> statusListPending = new LinkedList<>();
        Status status = new Status(team, cypher, CypherStatus.SOLVED);
        List<Status> statusListSolved = new LinkedList<>();
        statusListSolved.add(status);
        when(statusService.getPendingCyphers(team)).thenReturn(statusListPending);
        when(statusService.getAllByTeam(any())).thenReturn(statusListSolved);

        assertEquals("Hra ukončena", TeamProgressFactory.create(team, statusService).toString());
    }

    private void addStatusIntoList(final List<Status> dataset, final Cypher cypher, final Team team, final CypherStatus value) {
        final Status status = new Status();

        status.setCypher(cypher);
        status.setTeam(team);
        status.setCypherStatus(value);

        dataset.add(status);
    }

    private Cypher createCypherWithHints() {
        final Cypher cypher = new Cypher();

        final Hint hint1 = new Hint();
        hint1.setCypher(cypher);
        hint1.setHintId(1L);
        hint1.setText("foo");
        hint1.setValue(1);

        final Hint hint2 = new Hint();
        hint2.setCypher(cypher);
        hint2.setHintId(2L);
        hint2.setText("bar");
        hint2.setValue(3);

        List<Hint> hintList = new ArrayList<>();
        hintList.add(hint1);
        hintList.add(hint2);

        cypher.setHints(hintList);

        return cypher;
    }
}
