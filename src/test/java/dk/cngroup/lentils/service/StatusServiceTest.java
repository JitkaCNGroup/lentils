package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.StatusKey;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.CypherRepository;
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

import java.util.LinkedList;
import java.util.List;

import static dk.cngroup.lentils.service.ObjectGenerator.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class StatusServiceTest {

    @InjectMocks
    StatusService service;

    @Mock
    CypherRepository cypherRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    StatusRepository statusRepository;

    @Autowired
    private ObjectGenerator generator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void markCypherSolvedTest() {
        List<Status> statusList = fillTeamCypherAndStatusTables();
        Status status = getTestedProgressFromList(TEAM_NAME + TESTED_TEAM, TESTED_STAGE, statusList);

        when(statusRepository.findByTeamIdAndCypherId(status.getTeam().getId(), status.getCypher().getId()))
                .thenReturn(status);
        when(statusRepository.save(status)).thenReturn(status);
        service.markCypherSolvedForTeam(status.getTeam().getId(), status.getCypher().getId());

        assertEquals(CypherStatus.SOLVED, status.getCypherStatus());
    }

    @Test
    public void markCypherSkippedTest() {
        List<Status> statusList = fillTeamCypherAndStatusTables();
        Status status = getTestedProgressFromList(TEAM_NAME + TESTED_TEAM, TESTED_STAGE, statusList);

        when(statusRepository.findByTeamIdAndCypherId(status.getTeam().getId(), status.getCypher().getId()))
                .thenReturn(status);
        when(statusRepository.save(status)).thenReturn(status);
        service.markCypherSkippedForTeam(status.getTeam().getId(), status.getCypher().getId());

        assertEquals(CypherStatus.SKIPPED, status.getCypherStatus());
    }

    @Test
    public void getStatusOfAllTeamsTest() {

        List<Status> statusList = fillTeamCypherAndStatusTables();
        when(statusRepository.findAll()).thenReturn(statusList);

        assertEquals(NUMBER_OF_CYPHERS * NUMBER_OF_TEAMS, service.viewTeamsProgress().size());
    }

    @Test
    public void getScoreTest() {
        List<Status> statusList = fillTeamCypherAndStatusTables();
        Team team = generator.generateTeam();

        List<Status> statusTeamList = new LinkedList<>();
        for (Status status : statusList) {
            if (status.getTeam().getName().equals(TEAM_NAME + TESTED_TEAM)) {
                statusTeamList.add(status);
            }
        }

        when(statusRepository.findByTeam(team)).thenReturn(statusTeamList);
        assertEquals(50, service.getScore(team));

    }

    private List<Status> fillTeamCypherAndStatusTables() {
        List<Team> teams = generator.generateTeamList();
        when(teamRepository.saveAll(teams)).thenReturn(teams);

        List<Cypher> cyphers = generator.generateCypherList(NUMBER_OF_CYPHERS);
        when(cypherRepository.saveAll(cyphers)).thenReturn(cyphers);

        List<Status> statusList = new LinkedList<>();
        for (Team team : teams) {
            for (Cypher cypher : cyphers) {
                StatusKey statusKey = new StatusKey(cypher.getId(), team.getId());
                statusList.add(new Status(statusKey, team, cypher, CypherStatus.SOLVED));
            }
        }

        when(statusRepository.saveAll(statusList)).thenReturn(statusList);
        return statusList;
    }

    private Status getTestedProgressFromList(String nameOfTestedTeam, int testedStage, List<Status> statusList) {
        for (Status status : statusList) {
            if (status.getCypher().getStage() == testedStage
                    && status.getTeam().getName().equals(nameOfTestedTeam)) {
                return status;
            }
        }
        return null;
    }
}
