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
        List<Progress> progressList = fillProgressTable();

        Progress progress1 = null;
        for (Progress progress : progressList) {
            if (progress.getTeam().getName().equals(TEAM_NAME + TESTED_TEAM)
                    && progress.getCypher().getStage() == TESTED_STAGE) {
                progress1 = progress;
            }
        }

        when(progressRepository.save(progress1)).thenReturn(progress1);
        service.markCypherSolved(progress1);

        assertEquals(CypherStatus.SOLVED, progress1.getCypherStatus());
    }

    @Test
    public void markCypherSkippedTest() {
        List<Progress> progressList = fillProgressTable();

        Progress progress1 = null;
        for (Progress progress : progressList) {
            if (progress.getTeam().getName().equals(TEAM_NAME + TESTED_TEAM)
                    && progress.getCypher().getStage() == TESTED_STAGE) {
                progress1 = progress;
            }
        }

        when(progressRepository.save(progress1)).thenReturn(progress1);
        service.markCypherSkipped(progress1);

        assertEquals(CypherStatus.SKIPPED, progress1.getCypherStatus());
    }

    @Test
    public void getProgressOfAllTeamsTest() {

        List<Progress> progressList = fillProgressTable();
        when(progressRepository.findAll()).thenReturn(progressList);

        assertEquals(NUMBER_OF_CYPHERS * NUMBER_OF_TEAMS, service.viewTeamsProgress().size());
    }

    @Test
    public void getScoreTest() {
        List<Progress> progressList = fillProgressTable();
        Team team = generator.generateTeam();

        List<Progress> progressTeamList = new LinkedList<>();
        for (Progress progress : progressList) {
            if (progress.getTeam().getName().equals(TEAM_NAME + TESTED_TEAM)) {
                progressTeamList.add(progress);
            }
        }
        when(progressRepository.findByTeam(team)).thenReturn(progressTeamList);
        assertEquals(50, service.getScore(team));

    }

    private List<Progress> fillProgressTable() {
        List<Team> teams = generator.generateTeamList();
        when(teamRepository.saveAll(teams)).thenReturn(teams);

        List<Cypher> cyphers = generator.generateCypherList(NUMBER_OF_CYPHERS);
        when(cypherRepository.saveAll(cyphers)).thenReturn(cyphers);

        List<Progress> progressList = new LinkedList<>();
        for (Team team : teams) {
            for (Cypher cypher : cyphers) {
                ProgressKey progressKey = new ProgressKey(cypher.getId(), team.getId());
                progressList.add(new Progress(progressKey, team, cypher, CypherStatus.SOLVED));
            }
        }

        when(progressRepository.saveAll(progressList)).thenReturn(progressList);
        return progressList;
    }
}