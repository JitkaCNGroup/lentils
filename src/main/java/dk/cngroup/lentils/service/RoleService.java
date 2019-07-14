package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Role;
import dk.cngroup.lentils.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> setRole(final String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(role));
        return roles;
    }
}
