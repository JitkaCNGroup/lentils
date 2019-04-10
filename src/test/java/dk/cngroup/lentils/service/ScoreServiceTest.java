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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class ScoreServiceTest {

    @InjectMocks
    ScoreService scoreService;

    @Autowired
    private ObjectGenerator generator;

    @Mock
    private HintTakenService hintTakenService;

    @Mock
    private StatusService statusService;

    @Mock
    private CypherService cypherService;

    private Team team;
    private Cypher cypher;

    @Test
    public void getScoreForOneCypherSolvedNoHints() {

        when(hintTakenService.getHintScore(team, cypher)).thenReturn (0);
        when(statusService.getStatusScore(team, cypher)).thenReturn (10);

        assertEquals(10, scoreService.getScoreByTeamAndCypher(team, cypher));
    }

    @Test
    public void getScoreForOneCypherSolvedOneHint() {

        when(hintTakenService.getHintScore(team, cypher)).thenReturn (5);
        when(statusService.getStatusScore(team, cypher)).thenReturn (10);

        assertEquals(5, scoreService.getScoreByTeamAndCypher(team, cypher));
    }

    @Test
    public void getScoreForOneCypherSolvedMoreHints() {

        when(hintTakenService.getHintScore(team, cypher)).thenReturn (12);
        when(statusService.getStatusScore(team, cypher)).thenReturn (10);

        assertEquals(-2, scoreService.getScoreByTeamAndCypher(team, cypher));
    }

    @Test
    public void getScoreForOneCypherSkippedNoHint() {

        when(hintTakenService.getHintScore(team, cypher)).thenReturn (0);
        when(statusService.getStatusScore(team, cypher)).thenReturn (0);

        assertEquals(0, scoreService.getScoreByTeamAndCypher(team, cypher));
    }

    @Test
    public void getScoreForOneCypherSkippedWithHint() {

        when(hintTakenService.getHintScore(team, cypher)).thenReturn (8);
        when(statusService.getStatusScore(team, cypher)).thenReturn (0);

        assertEquals(-8, scoreService.getScoreByTeamAndCypher(team, cypher));
    }

    @Test
    public void getScoreByTeamWithCyphers() {

        List<Cypher> cyphers = generator.generateCypherList(5);
        when(cypherService.getAll()).thenReturn (cyphers);
        when(hintTakenService.getHintScore(any(), any())).thenReturn (3);
        when(statusService.getStatusScore(any(),any())).thenReturn (10);

        assertEquals(35, scoreService.getScoreByTeam(team));
    }

    @Test
    public void getScoreByTeamNoCyphers() {

        List<Cypher> cyphers = generator.generateCypherList(0);
        when(cypherService.getAll()).thenReturn (cyphers);
        when(hintTakenService.getHintScore(any(), any())).thenReturn (3);
        when(statusService.getStatusScore(any(),any())).thenReturn (10);

        assertEquals(0, scoreService.getScoreByTeam(team));
    }

    private Hint createHintWithValue(int value) {
        return new Hint("abcd", value, cypher);
    }

}