package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class, ObjectGenerator.class})
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository repository;

    @Autowired
    private ObjectGenerator generator;

    @Test
    public void countEmptyDatabaseTest() {
        assertEquals(0, repository.count());
    }

    @Test
    public void addTest() {
        getAnySaved();

        assertEquals(1, repository.count());
    }

    @Test
    public void findByIdTest() {
        Team teamPlaceOrig = getAnySaved();

        Optional<Team> teamPlace = repository.findById(teamPlaceOrig.getId());

        assertNotNull(teamPlace);
        assertEquals(teamPlace.get(), teamPlaceOrig);
    }

    @Test
    public void findAllTest() {
        getAllSavedTeams();

        List<Team> teamsFound = repository.findAll();

        assertEquals(generator.NUMBER_OF_TEAMS, teamsFound.size());
    }

    @Test
    public void deleteAllTest() {
        getAllSavedTeams();

        repository.deleteAll();

        assertEquals(0, repository.count());
    }

    @Test
    public void deleteOneTest() {
        Team team = repository.save(generator.generateTeam());

        repository.deleteById(team.getId());

        assertEquals(0, repository.count());
    }

    private Team getAnySaved() {
        return repository.save(generator.generateTeam());
    }

    private List<Team> getAllSavedTeams() {
        return repository.saveAll(generator.generateTeamList());
    }

}