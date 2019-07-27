package dk.cngroup.lentils.service;

import dk.cngroup.lentils.LentilsApplication;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
@Transactional
public class CustomUserDetailsServiceIntegrationTest {
    private static final String VALID_USERNAME = "validUsername";
    private static final String INVALID_USERNAME = "invalidUsername";

    private ObjectGenerator objectGenerator = new ObjectGenerator();

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private User dummyUser;

    @Before
    public void setup() {
        user = objectGenerator.createUser(VALID_USERNAME);
        userRepository.save(user);

        dummyUser = objectGenerator.createUser("dummy");
        userRepository.save(dummyUser);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testInvalidUser() {
        customUserDetailsService.loadUserByUsername(INVALID_USERNAME);
    }

    @Test
    public void testValidUser() {
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(VALID_USERNAME);

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    public void testUserAccountIsNotLocked() {
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(VALID_USERNAME);

        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void testUserAccountIsNotExpired() {
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(VALID_USERNAME);

        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void testUserAccountIsEnabled() {
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(VALID_USERNAME);

        assertTrue(userDetails.isEnabled());
    }

    @Test
    public void testCredentialsNonExpired() {
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(VALID_USERNAME);

        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testCorrectPasswordIsReturned() {
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(VALID_USERNAME);

        assertEquals(user.getPassword(), userDetails.getPassword());
    }
}
