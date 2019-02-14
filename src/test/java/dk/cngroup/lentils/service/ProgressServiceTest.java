package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Progress;
import dk.cngroup.lentils.entity.ProgressKey;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.ProgressRepository;
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

import java.util.*;

import static dk.cngroup.lentils.service.ObjectGenerator.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class ProgressServiceTest {

    @InjectMocks
    ProgressService service;

    @Mock
    CypherRepository cypherRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    ProgressRepository progressRepository;

    @Autowired
    private ObjectGenerator generator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void markCypherSolvedTest() {
    }

    @Test
    public void markCypherSkippedTest() {
    }

    @Test
    public void getProgressOfAllTeamsTest() {

        List<Team> teams = generator.generateTeamList();
        when(teamRepository.saveAll(teams)).thenReturn(teams);

        List<Cypher> cyphers = generator.generateCypherList(NUMBER_OF_CYPHERS);
        when(cypherRepository.saveAll(cyphers)).thenReturn(cyphers);

        List<Progress> progressList  = new LinkedList<>() ;
        for (Team team: teams) {
            for (Cypher cypher : cyphers) {
                ProgressKey progressKey = new ProgressKey( cypher.getId(), team.getId());
                progressList.add(new Progress(progressKey, team, cypher));
            }
        }

        when(progressRepository.saveAll(progressList)).thenReturn(progressList);

        when(progressRepository.findAll()).thenReturn(progressList);

        assertEquals(NUMBER_OF_CYPHERS * NUMBER_OF_TEAMS, service.viewTeamsProgress().size());
    }
    /*      List<Team> teams = generator.generateTeamList();
        when(repository.saveAll(teams)).thenReturn(teams);

        when(repository.findAll()).thenReturn(teams);
        List<Team> teamsFound = service.getAll();*/
/*
    @Test
    public void getScoreForTeamTest() {
        generator.generateTeam();
        service.getScore();
    }*/
}