package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ScoreServiceTest {

    @InjectMocks
    ScoreService scoreService;

    private ObjectGenerator generator = new ObjectGenerator();

    @Mock
    private HintTakenService hintTakenService;

    @Mock
    private StatusService statusService;

    @Mock
    private CypherService cypherService;

    private Team team;
    private Cypher cypher;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getScoreForOneCypherSolvedNoHints() {
        verifyScoreOfOneCypher(0, 10, 10);
    }

    @Test
    public void getScoreForOneCypherSolvedOneHint() {
        verifyScoreOfOneCypher(5, 10, 5);
    }

    @Test
    public void getScoreForOneCypherSolvedMoreHints() {
        verifyScoreOfOneCypher(12, 10, -2);
    }

    @Test
    public void getScoreForOneCypherSkippedNoHint() {
        verifyScoreOfOneCypher(0, 0, 0);
    }

    @Test
    public void getScoreForOneCypherSkippedWithHint() {
        verifyScoreOfOneCypher(8, 0, -8);
    }

    @Test
    public void getScoreByTeamWithCyphers() {
        verifyScoreOfTeam(5, 3, 10, 35);
   }

    @Test
    public void getScoreByTeamNoCyphers() {
        verifyScoreOfTeam(5, 3, 10, 35);
    }

    public void verifyScoreOfOneCypher(int hintScore, int statusScore, int expected) {
        when(hintTakenService.getHintScore(team, cypher)).thenReturn(hintScore);
        when(statusService.getStatusScore(team, cypher)).thenReturn(statusScore);

        assertEquals(expected, scoreService.getScoreByTeamAndCypher(team, cypher));
    }

    public void verifyScoreOfTeam(int numberOfCyphers, int hintScore, int statusScore, int expected) {
        List<Cypher> cyphers = generator.generateCypherList(numberOfCyphers);
        when(cypherService.getAll()).thenReturn(cyphers);
        when(hintTakenService.getHintScore(any(), any())).thenReturn(hintScore);
        when(statusService.getStatusScore(any(),any())).thenReturn(statusScore);

        assertEquals(expected, scoreService.getScoreByTeam(team));
    }
}