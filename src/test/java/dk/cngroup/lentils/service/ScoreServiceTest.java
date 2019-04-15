package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.*;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.HintTakenRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
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
        getScoreForOneCypher(0, 10, 10);
    }

    @Test
    public void getScoreForOneCypherSolvedOneHint() {
        getScoreForOneCypher(5, 10, 5);
    }

    @Test
    public void getScoreForOneCypherSolvedMoreHints() {
        getScoreForOneCypher(12, 10, -2);
    }

    @Test
    public void getScoreForOneCypherSkippedNoHint() {
        getScoreForOneCypher(0, 0, 0);
    }

    @Test
    public void getScoreForOneCypherSkippedWithHint() {
        getScoreForOneCypher(8, 0, -8);
    }

    @Test
    public void getScoreByTeamWithCyphers() {
        getScoreByTeam(5, 3, 10, 35);
   }

    @Test
    public void getScoreByTeamNoCyphers() {
        getScoreByTeam(5, 3, 10, 35);
    }

    public void getScoreForOneCypher(int hintScore, int statusScore, int expected) {
        when(hintTakenService.getHintScore(team, cypher)).thenReturn(hintScore);
        when(statusService.getStatusScore(team, cypher)).thenReturn(statusScore);

        assertEquals(expected, scoreService.getScoreByTeamAndCypher(team, cypher));
    }

    public void getScoreByTeam(int numberOfCyphers, int hintScore, int statusScore, int expected) {
        List<Cypher> cyphers = generator.generateCypherList(numberOfCyphers);
        when(cypherService.getAll()).thenReturn(cyphers);
        when(hintTakenService.getHintScore(any(), any())).thenReturn(hintScore);
        when(statusService.getStatusScore(any(),any())).thenReturn(statusScore);

        assertEquals(expected, scoreService.getScoreByTeam(team));
    }
}