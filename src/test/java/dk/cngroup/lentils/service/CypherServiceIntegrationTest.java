package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Role;
import dk.cngroup.lentils.entity.Status;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.HintRepository;
import dk.cngroup.lentils.repository.RoleRepository;
import dk.cngroup.lentils.repository.StatusRepository;
import dk.cngroup.lentils.repository.TeamRepository;
import dk.cngroup.lentils.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class CypherServiceIntegrationTest {

    @Autowired
    private CypherService testedService;
    @Autowired
    private CypherRepository cypherRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private HintRepository hintRepository;
    @Autowired
    private RoleRepository roleRepository;

    final ObjectGenerator objectGenerator = new ObjectGenerator();

    @Test
    public void getNextCypherWhenHavingSuccessiveNumberTest() {
        final Cypher first = getCypherWithStageNumber(1);
        final Cypher next = getCypherWithStageNumber(2);
        final Cypher random = getCypherWithStageNumber(3);

        final Cypher result = testedService.getNext(first.getStage());

        assertNotNull(result);
        assertEquals(next.getStage(), result.getStage());
    }

    @Test
    public void getNextCypherWhenHavingArbitraryStageNumberTest() {
        final Cypher random = getCypherWithStageNumber(1);
        final Cypher first = getCypherWithStageNumber(4);
        final Cypher next = getCypherWithStageNumber(9);

        final Cypher result = testedService.getNext(first.getStage());

        assertNotNull(result);
        assertEquals(next.getStage(), result.getStage());
    }

    @Test
    public void getNextCypherWhenNextDoesNotExistsTest() {
        final Cypher first = getCypherWithStageNumber(4);
        final Cypher last = getCypherWithStageNumber(9);

        final Cypher result = testedService.getNext(last.getStage());

        assertNull(result);
    }

    @Test
    public void getNextCypherWhenCyphersHasBeenCreatedInRandomOrder() {
        final Cypher first = getCypherWithStageNumber(1);
        final Cypher fourth = getCypherWithStageNumber(4);
        final Cypher third = getCypherWithStageNumber(3);
        final Cypher second = getCypherWithStageNumber(2);


        final Cypher result = testedService.getNext(first.getStage());

        assertNotNull(result);
        assertEquals(second.getStage(), result.getStage());
    }

    @Test
    public void getAllNotAssignedToOrganizerTest() {
        Cypher cypher1 = objectGenerator.generateValidCypher();
        Cypher cypher2 = objectGenerator.generateValidCypher();
        Cypher cypher3 = objectGenerator.generateValidCypher();
        User assertedOrganizer = createUser("org", "pwd", "ORGANIZER");
        User organizer = createUser("org2", "pwd2", "ORGANIZER");
        List<User> asserted = new ArrayList<>();
        asserted.add(assertedOrganizer);
        asserted.add(organizer);
        cypher1.setOrganizers(asserted);
        List<Cypher> expectedCyphers = new ArrayList<>();
        expectedCyphers.add(cypher2);
        expectedCyphers.add(cypher3);

        userRepository.save(assertedOrganizer);
        userRepository.save(organizer);
        cypherRepository.save(cypher1);
        cypherRepository.save(cypher2);
        cypherRepository.save(cypher3);

        assertEquals(expectedCyphers, testedService.getAllNotAssignedToOrganizer(organizer));
    }

    /**
     * This tests #65
     * Cypher with Status record could not be deleted due to incorrect FK constrains.
     */
    @Test
    public void deleteCypher() {
        final Cypher cypher = getCypherWithStageNumber(1);
        final Team team = objectGenerator.generateValidTeam();
        final Status status = new Status();
        status.setCypherStatus(CypherStatus.PENDING);
        status.setCypher(cypher);
        status.setTeam(team);

        cypher.setHints(hintRepository.saveAll(objectGenerator.generateHintsForCypher(cypher)));
        teamRepository.saveAndFlush(team);
        statusRepository.saveAndFlush(status);

        final long cypherCount =  cypherRepository.count();
        final long statusCount = statusRepository.count();
        assertEquals(1, cypherCount);
        assertEquals(1, statusRepository.count());
        assertEquals(3, hintRepository.count());

        testedService.deleteById(cypher.getCypherId());

        assertEquals(cypherCount - 1, cypherRepository.count());
        assertEquals(statusCount - 1, statusRepository.count());
        assertEquals(0, hintRepository.count());
    }

    private Cypher getCypherWithStageNumber(final int stageNumber) {
        final Cypher cypher = objectGenerator.generateValidCypher();
        cypher.setStage(stageNumber);

        return cypherRepository.save(cypher);
    }

    private User createUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(role));
        user.setRoles(roles);
        return user;
    }
}
