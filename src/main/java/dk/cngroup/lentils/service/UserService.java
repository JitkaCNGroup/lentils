package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Role;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getOrganizers() {
        return userRepository.findAllByRolesRoleOrderByUsername("ORGANIZER");
    }

    public List<User> getOrganizersByIds(final List<Long> organizerIds) {
        return userRepository.findAllByUserIdInAndRolesRole(organizerIds, Role.ORGANIZER);
    }
}
