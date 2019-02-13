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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class TeamServiceTest {

    @InjectMocks
    TeamService service;

    @Mock
    TeamRepository repository;

    @Autowired
    private ObjectGenerator generator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTest() {
        Team teamOrig = getAnySaved();

        when(repository.save(teamOrig)).thenReturn(teamOrig);
        Team team = service.add(teamOrig);

        assertNotNull(team);
        assertEquals(teamOrig, team);
    }

    @Test
    public void getTest() {
        Team teamOrig = getAnySaved();

        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(teamOrig));
        Optional<Team> team = service.get(teamOrig.getId());

        assertNotNull(team);
    }

    @Test
    public void getAllTest() {
        List<Team> teams = generator.generateTeamList();
        when(repository.saveAll(teams)).thenReturn(teams);

        when(repository.findAll()).thenReturn(teams);
        List<Team> teamsFound = service.getAll();

        assertEquals(generator.NUMBER_OF_TEAMS, teamsFound.size());
    }

    @Test
    public void deleteAllTest() {
        List<Team> teams = generator.generateTeamList();
        when(repository.saveAll(teams)).thenReturn(teams);

        service.deleteAll();

        when(repository.count()).thenReturn(new Long(0));
        assertEquals(0, repository.count());
    }

    private Team getAnySaved() {
        Team teamOrig = generator.generateTeam();

        when(repository.save(teamOrig)).thenReturn(teamOrig);
        return service.add(teamOrig);
    }
}