package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.Role;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.repository.RoleRepository;
import dk.cngroup.lentils.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LentilsApplication.class)
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetOrganizersWhileTwoInDb() {
        createAndSaveFourUsers();
        assertEquals(4, userRepository.count());
        assertEquals(2, userService.getOrganizers().size());
    }

    @Test
    public void testGetOrganizersWhileFourInDb() {
        createAndSaveFourUsers();
        createAndSaveFourUsers();
        assertEquals(8, userRepository.count());
        assertEquals(4, userService.getOrganizers().size());
    }

    @Test
    public void testGetOrganizersWhileNoOrganizersInDb() {
        userRepository.save(createUser("first", "pwd", "ADMIN"));
        assertEquals(1, userRepository.count());
        assertEquals(0, userService.getOrganizers().size());
    }

    @Test
    public void testGetOrganizersByIdsWhileNoOrganizersInDb() {
        userRepository.save(createUser("first", "pwd", "ADMIN"));
        List<Long> organizerIds = new ArrayList<>();
        organizerIds.add(500L);
        organizerIds.add(501L);
        assertEquals(1, userRepository.count());
        List<User> organizers = userService.getOrganizersByIds(organizerIds);
        assertEquals(0, organizers.size());
    }

    private User createUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole(role));
        user.setRoles(roles);
        return user;
    }

    private void createAndSaveFourUsers () {
        userRepository.save(createUser("first", "pwd", "ORGANIZER"));
        userRepository.save(createUser("second", "pwd", "ORGANIZER"));
        userRepository.save(createUser("third", "pwd", "ADMIN"));
        userRepository.save(createUser("fourth", "pwd", "ADMIN"));
    }
}