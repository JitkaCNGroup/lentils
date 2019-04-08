package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class ScoreServiceTest {

    @InjectMocks
    StatusService statusService;

    @InjectMocks
    TeamService teamService;

    @InjectMocks
    HintTakenService hintTakenService;

    @InjectMocks
    CypherService cypherService;

    @Autowired
    private ObjectGenerator generator;

    @Test
    public void getScoreByTeamAndCypher() {

    }

    @Test
    public void getScoreByTeam() {
    }
}