package dk.cngroup.lentils.security;

import dk.cngroup.lentils.entity.Role;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomUserDetailsTest {
    private static final String USERNAME = "john doe";
    private static final String TEAM_NAME = "tractor";
    private static final String ROLE_NAME = "pilot";

    @Test
    public void testBasicCredentialsCanBeRetrieved() {
        final User user = new User();
        user.setUsername(USERNAME);
        final CustomUserDetails details = createUserDetails(user);

        assertEquals(USERNAME, details.getUsername());
    }

    @Test
    public void testTeamCanBeRetrieved() {
        final User user = new User();
        final Team team = new Team();
        team.setName(TEAM_NAME);
        user.setTeam(team);
        final CustomUserDetails details = createUserDetails(user);

        assertNotNull(details.getTeam());
        assertEquals(TEAM_NAME, details.getTeam().getName());
    }

    @Test
    public void testRolesCanBeRetrieved() {
        final User user = new User();
        final Role role = new Role();
        role.setRole(ROLE_NAME);
        user.setRoles(Collections.singleton(role));
        final CustomUserDetails details = createUserDetails(user);

        assertNotNull(details.getRoles());
        assertEquals(1, details.getRoles().size());
    }

    private CustomUserDetails createUserDetails(final User user) {
        // Same logic as in CustomUserDetailsService
        Optional<User> optionalUser = Optional.of(user);

        return optionalUser.map(CustomUserDetails::new).get();
    }
}
