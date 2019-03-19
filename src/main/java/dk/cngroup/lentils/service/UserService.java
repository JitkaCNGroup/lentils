package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final PasswordEncoder passwordEncoder,
                       final RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public void createUserForTeam(final Team team) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(team.getPin()));
        user.setUsername(team.getName());
        user.setRoles(roleService.setRole("USER"));
        userRepository.save(user);
    }
}
