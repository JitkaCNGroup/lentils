package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.TeamRepository;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class SearchServiceIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private SearchService searchService;

    private ObjectGenerator objectGenerator = new ObjectGenerator();

    @Before
    public void setup() {
        createVariousTeams();
        flushToIndexes();
    }

    @Test
    public void searchTeamOneResultTest(){
        List<Team> teams = searchService.searchTeams("Sparta");

        Assert.assertEquals(1, teams.size());
    }

    @Test
    public void searchTeamMultipleResultsTest() {
        List<Team> teams = searchService.searchTeams("Sevci");

        Assert.assertEquals(2, teams.size());
    }

    @Test
    public void searchTeamNoResultTest() {
        List<Team> teams = searchService.searchTeams("Pribram");

        Assert.assertEquals(0, teams.size());
    }

    private void createVariousTeams() {
        teamRepository.saveAndFlush(objectGenerator.generateSpecificTeam("Sparta","0123"));
        teamRepository.saveAndFlush(objectGenerator.generateSpecificTeam("Banik", "4567"));
        teamRepository.saveAndFlush(objectGenerator.generateSpecificTeam("SevciA", "8901"));
        teamRepository.saveAndFlush(objectGenerator.generateSpecificTeam("SevciB", "2345"));
    }

    private void flushToIndexes() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
            fullTextEntityManager.flushToIndexes();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
