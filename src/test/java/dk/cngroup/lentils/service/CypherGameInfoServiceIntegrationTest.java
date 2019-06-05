package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.view.CypherGameInfo;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class CypherGameInfoServiceIntegrationTest {
    @Autowired
    private CypherGameInfoService cypherGameInfoService;
    @Autowired
    private ObjectGenerator generator;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private CypherRepository cypherRepository;
    @Autowired
    private StatusRepository statusRepository;

    private Team team;

    @Before
    public void setup() {
        team = generator.generateValidTeam();
        teamRepository.saveAndFlush(team);
    }

    @Test
    public void testOnlyUnlockedCyphersAreFetched() {
        Cypher cypher1 = createAndSaveCypher(2, CypherStatus.SOLVED);
        Cypher cypher2 = createAndSaveCypher(3, CypherStatus.SKIPPED);
        Cypher cypher3 = createAndSaveCypher(3, CypherStatus.PENDING);
        Cypher cypher4 = createAndSaveCypher(1, CypherStatus.LOCKED);
        Cypher cypher5 = createAndSaveCypher(3, null);

        List<CypherGameInfo> gameInfos = cypherGameInfoService.getAllByTeamIdAndStatusIsNotLocked(team.getTeamId());
        assertThat(gameInfos.size(), is(3));

        List<CypherStatus> allowedStatus = Arrays.asList(CypherStatus.SOLVED, CypherStatus.SKIPPED, CypherStatus.PENDING);
        for(CypherGameInfo gameInfo : gameInfos) {
            assertNotNull(gameInfo.getStatus());
            assertThat(gameInfo.getStatus(), isIn(allowedStatus));
        }
    }

    @Test
    public void testGetCyphersInOrder_whenSortedAsc() {
        Cypher cypher1 = createAndSaveCypher(1, CypherStatus.SOLVED);
        Cypher cypher2 = createAndSaveCypher(2, CypherStatus.SOLVED);
        Cypher cypher3 = createAndSaveCypher(3, CypherStatus.SOLVED);

        List<CypherGameInfo> gameInfos = cypherGameInfoService.getAllByTeamIdAndStatusIsNotLocked(team.getTeamId());

        assertThat(gameInfos.size(), is(3));
        assertThat(gameInfos.get(0).getCypherId(), is(cypher1.getCypherId()));
        assertThat(gameInfos.get(1).getCypherId(), is(cypher2.getCypherId()));
        assertThat(gameInfos.get(2).getCypherId(), is(cypher3.getCypherId()));
    }

    @Test
    public void testGetCyphersInOrder_whenSortedDesc() {
        Cypher cypher1 = createAndSaveCypher(3, CypherStatus.SOLVED);
        Cypher cypher2 = createAndSaveCypher(2, CypherStatus.SOLVED);
        Cypher cypher3 = createAndSaveCypher(1, CypherStatus.SOLVED);

        List<CypherGameInfo> gameInfos = cypherGameInfoService.getAllByTeamIdAndStatusIsNotLocked(team.getTeamId());

        assertThat(gameInfos.size(), is(3));
        assertThat(gameInfos.get(2).getCypherId(), is(cypher1.getCypherId()));
        assertThat(gameInfos.get(1).getCypherId(), is(cypher2.getCypherId()));
        assertThat(gameInfos.get(0).getCypherId(), is(cypher3.getCypherId()));
    }

    private Cypher createAndSaveCypher(final int stage, final CypherStatus status) {
        Cypher cypher = generator.generateValidCypher();
        cypher.setStage(stage);
        cypher = cypherRepository.saveAndFlush(cypher);

        if (status != null) {
            final Status statusEntity = new Status();
            statusEntity.setCypher(cypher);
            statusEntity.setTeam(team);
            statusEntity.setCypherStatus(status);

            statusRepository.saveAndFlush(statusEntity);
        }

        return cypher;
    }
}
