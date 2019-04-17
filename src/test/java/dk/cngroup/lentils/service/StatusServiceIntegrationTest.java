package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class StatusServiceIntegrationTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CypherRepository cypherRepository;

    @Autowired
    private StatusService statusService;

    private ObjectGenerator objectGenerator = new ObjectGenerator();

    @Test
    public void initializeStatusForTeamAndCypher() {
        Team team = objectGenerator.generateNewTeam();
        teamRepository.save(team);
        Cypher cypher = objectGenerator.generateNewCypher();
        cypherRepository.save(cypher);
        statusService.initializeStatusForTeamAndCypher(cypher, team);

        assertEquals("LOCKED", statusService.getStatusNameByTeamAndCypher(team, cypher));
    }
}
