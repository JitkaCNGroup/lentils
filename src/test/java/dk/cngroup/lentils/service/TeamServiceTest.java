package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.TeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static dk.cngroup.lentils.service.ObjectGenerator.TEAM_NAME;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class TeamServiceTest {
    private static final int TESTED_TEAM = 5;

    @InjectMocks
    TeamService service;

    @Mock
    TeamRepository repository;

    @Mock
    RoleService roleService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectGenerator generator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void pinIsgeneratedTest() {
        Team team = new Team(TEAM_NAME + TESTED_TEAM, 5);
        when(repository.save(team)).thenReturn(team);
        Team teamNew = service.save(team);

        assertNotNull(team.getPin());
    }

    @Test
    public void deleteAllTest() {
        List<Team> teams = generator.generateTeamList();
        when(repository.saveAll(teams)).thenReturn(teams);

        service.deleteAll();

        when(repository.count()).thenReturn(new Long(0));
        assertEquals(0, repository.count());
    }

}