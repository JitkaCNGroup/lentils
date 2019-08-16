package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Role;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class})
public class CypherRepositoryIntegrationTest {
    @Autowired
    private CypherRepository cypherRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectGenerator objectGenerator;

    @Before
    public void setUp() {
        cypherRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void findFirstByStageTest() {
        Cypher cypher1 = new Cypher(new Point(1.1,1.1), 1, "bonus1", "descr1", "address1");
        Cypher cypher2 = new Cypher(new Point(1.1,1.1), 2, "bonus2", "descr2", "address2");
        Cypher cypher3 = new Cypher(new Point(1.1,1.1), 3, "bonus3", "descr3", "address3");
        Cypher cypher4 = new Cypher(new Point(1.1,1.1), 4, "bonus4", "descr4", "address4");
        Cypher cypher5 = new Cypher(new Point(1.1,1.1), 5, "bonus5", "descr5", "address5");

        cypherRepository.save(cypher5);
        cypherRepository.save(cypher4);
        cypherRepository.save(cypher3);
        cypherRepository.save(cypher2);
        cypherRepository.save(cypher1);

        assertEquals(cypher1, cypherRepository.findFirstByOrderByStageAsc());
    }

    @Test
    public void findDistinctByOrganizersIsNotNullTest() {
        Cypher cypher1 = objectGenerator.generateNewCypher();
        Cypher cypher2 = objectGenerator.generateNewCypher();
        Cypher cypher3 = objectGenerator.generateNewCypher();
        User organizer = createUser("name", "pwd", "ORGANIZER");
        List<User> organizers = new ArrayList<>();
        organizers.add(organizer);
        cypher1.setOrganizers(organizers);
        List<Cypher> expectedCyphers = new ArrayList<>();
        expectedCyphers.add(cypher1);

        userRepository.save(organizer);
        cypherRepository.save(cypher1);
        cypherRepository.save(cypher2);
        cypherRepository.save(cypher3);

        assertFalse(cypherRepository.findDistinctByOrganizersIsNotNull().isEmpty());
        assertEquals(expectedCyphers, cypherRepository.findDistinctByOrganizersIsNotNull());
    }

    @Test
    public void deleteCypherWithOneAssignedOrganizerTest() {
        Cypher cypher = objectGenerator.generateValidCypher();
        User user = userRepository.save(createUser("name", "pwd", "ORGANIZER"));
        List<User> organizers = new ArrayList<>();
        organizers.add(user);
        cypher.setOrganizers(organizers);
        cypherRepository.save(cypher);

        assertEquals(1, cypherRepository.count());
        assertEquals(1, userRepository.count());
        cypherRepository.delete(cypher);
        assertEquals(0, cypherRepository.count());
        assertEquals(1, userRepository.count());
    }

    @Test
    public void deleteCypherWithTwoAssignedOrganizersTest() {
        Cypher cypher = objectGenerator.generateValidCypher();
        User user = userRepository.save(createUser("name", "pwd", "ORGANIZER"));
        User user2 = userRepository.save(createUser("name2", "pwd2", "ORGANIZER"));
        List<User> organizers = new ArrayList<>();
        organizers.add(user);
        organizers.add(user2);
        cypher.setOrganizers(organizers);
        cypherRepository.save(cypher);

        assertEquals(1, cypherRepository.count());
        assertEquals(2, userRepository.count());
        cypherRepository.delete(cypher);
        assertEquals(0, cypherRepository.count());
        assertEquals(2, userRepository.count());
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
