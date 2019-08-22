package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Role;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.CypherRepository;
import dk.cngroup.lentils.repository.RoleRepository;
import dk.cngroup.lentils.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = {LentilsApplication.class, DataConfig.class})
public class OrganizatorServiceIntegrationTest {

    @Autowired
    private OrganizerService organizerService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CypherRepository cypherRepository;

    ObjectGenerator objectGenerator = new ObjectGenerator();

    @Test
    public void getOrganizerByIdTest() {
        User organizer = createUser("org", "pwd", "ORGANIZER");
        User admin = createUser("adm", "pwd", "ADMIN");
        List<User> expected = new ArrayList<>();
        expected.add(organizer);

        userRepository.save(organizer);
        userRepository.save(admin);

        Assert.assertEquals(organizer, organizerService.getOrganizerById(organizer.getUserId()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getOrganizerByIdShouldFailTest() {
        User organizer = createUser("org", "pwd", "ORGANIZER");
        User admin = createUser("adm", "pwd", "ADMIN");

        userRepository.save(organizer);
        userRepository.save(admin);
        organizerService.getOrganizerById(admin.getUserId());
    }

    @Test
    public void getOrganizersTest() {
        int organizerCount = organizerService.getOrganizers().size();
        User organizer = createUser("org", "pwd", "ORGANIZER");
        User admin = createUser("adm", "pwd", "ADMIN");
        User player = createUser("ply", "pwd", "PLAYER");

        userRepository.save(organizer);
        userRepository.save(admin);
        userRepository.save(player);

        Assert.assertEquals(organizerCount + 1, organizerService.getOrganizers().size());
        Assert.assertTrue(organizerService.getOrganizers().contains(organizer));
    }

    @Test
    public void getOrganizersDtosTest() {
        int organizerDtosCount = organizerService.getOrganizerDtos().size();
        User organizer = createUser("org", "pwd", "ORGANIZER");
        User admin = createUser("adm", "pwd", "ADMIN");
        User player = createUser("ply", "pwd", "PLAYER");

        userRepository.save(organizer);
        userRepository.save(admin);
        userRepository.save(player);

        Assert.assertEquals(organizerDtosCount + 1, organizerService.getOrganizerDtos().size());
        Assert.assertTrue(organizerService.getOrganizerDtos()
                .stream()
                .anyMatch(o -> o.getUsername().equals(organizer.getUsername())));
    }

    @Test
    public void saveOrganizerWithoutCyphersTest() {
        User user = new User();
        user.setPassword("pwd");
        user.setUsername("org");

        organizerService.saveOrganizer(user, null);

        Assert.assertTrue(userRepository.findAllByRolesNameOrderByUsername("ORGANIZER").contains(user));
    }

    @Test
    public void saveOrganizerAddToCyphersTest() {
        Cypher cypher1 = objectGenerator.generateValidCypher();
        Cypher cypher2 = objectGenerator.generateValidCypher();
        cypherRepository.save(cypher1);
        cypherRepository.save(cypher2);
        List<Long> cypherIds = new ArrayList<>();
        cypherIds.add(cypher1.getCypherId());
        cypherIds.add(cypher2.getCypherId());
        User user = new User();
        user.setPassword("pwd");
        user.setUsername("org");
        List<User> organizers = new ArrayList<>();
        organizers.add(user);

        organizerService.saveOrganizer(user, cypherIds);

        Assert.assertTrue(userRepository.findAllByRolesNameOrderByUsername("ORGANIZER").contains(user));
        Assert.assertEquals(organizers, cypherRepository.findById(cypher1.getCypherId()).get().getOrganizers());
        Assert.assertEquals(organizers, cypherRepository.findById(cypher2.getCypherId()).get().getOrganizers());
    }

    @Test
    public void deleteOrganizerWithoutTiesToCyphersTest() {
        User user = new User();
        user.setPassword("pwd");
        user.setUsername("org");
        organizerService.saveOrganizer(user, null);

        organizerService.delete(user.getUserId());

        Assert.assertFalse(userRepository.findAll().contains(user));
    }

    @Test
    public void deleteOrganizerWithTiesToCyphersTest() {
        Cypher cypher1 = objectGenerator.generateValidCypher();
        Cypher cypher2 = objectGenerator.generateValidCypher();
        cypherRepository.save(cypher1);
        cypherRepository.save(cypher2);
        List<Long> cypherIds = new ArrayList<>();
        cypherIds.add(cypher1.getCypherId());
        cypherIds.add(cypher2.getCypherId());
        User user = new User();
        user.setPassword("pwd");
        user.setUsername("org");
        organizerService.saveOrganizer(user, cypherIds);

        organizerService.delete(user.getUserId());

        Assert.assertFalse(cypher1.getOrganizers().contains(user));
        Assert.assertFalse(cypher2.getOrganizers().contains(user));
        Assert.assertFalse(userRepository.findAll().contains(user));
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
