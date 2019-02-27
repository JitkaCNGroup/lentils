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
    private static final int TESTED_STAGE = 3;
    private static final int TESTED_TEAM = 5;

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
    public void getStatusOfAllTeamsTest() {

        List<Status> statusList = fillTeamCypherAndStatusTables();
        when(statusRepository.findAll()).thenReturn(statusList);

        assertEquals(NUMBER_OF_CYPHERS * NUMBER_OF_TEAMS, service.getAll().size());
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
