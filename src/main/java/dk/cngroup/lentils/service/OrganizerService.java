package dk.cngroup.lentils.service;

import dk.cngroup.lentils.dto.OrganizerFormDTO;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.UserRepository;
import dk.cngroup.lentils.service.convertors.OrganizerMapper;
import dk.cngroup.lentils.util.UsernameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizerService {

    private static final String ROLE_NAME_ORGANIZER = "ORGANIZER";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final CypherService cypherService;
    private final OrganizerMapper organizerMapper;

    @Autowired
    public OrganizerService(final UserRepository userRepository,
                            final RoleService roleService,
                            final PasswordEncoder passwordEncoder,
                            final CypherService cypherService,
                            final OrganizerMapper organizerMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.cypherService = cypherService;
        this.organizerMapper = organizerMapper;
    }

    public User getOrganizerById(final Long id) {
        Optional<User> organizer = userRepository.findByUserIdAndRolesName(id, ROLE_NAME_ORGANIZER);
        if (organizer.isPresent()) {
            return organizer.get();
        }
        throw new ResourceNotFoundException(User.class.getSimpleName(), id);
    }

    public List<User> getOrganizers() {
        return userRepository.findAllByRolesNameOrderByUsername(ROLE_NAME_ORGANIZER);
    }

    public List<OrganizerFormDTO> getOrganizerDtos() {
        return getOrganizers().stream()
                .map(organizerMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveOrganizer(final User user,
                              final List<Long> cypherIds) {
        user.setRoles(roleService.setRole(ROLE_NAME_ORGANIZER));
        user.setUsername(UsernameUtils.generateUsername(user.getUsername()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        if (cypherIds != null) {
            cypherService.addOrganizerToCyphers(user, cypherIds);
        }
    }

    @Transactional
    public void delete(final Long id) {
        cypherService.getAllWithOrganizer().stream()
                .filter(cypher -> cypher.getOrganizers()
                        .contains(userRepository.findById(id).get()))
                .forEach(cypher -> cypher.getOrganizers()
                        .remove(userRepository.findById(id).get()));
        userRepository.deleteById(id);
    }

    public void checkUsernameIsUnique(final OrganizerFormDTO organizerFormDto, final BindingResult bindingResult) {
        if (!isUsernameUnique(organizerFormDto)) {
            FieldError error = new FieldError(
                    "command",
                    "username",
                    organizerFormDto.getUsername(),
                    true,
                    null,
                    null,
                    "Zadané jméno již existuje");
            bindingResult.addError(error);
        }
    }

    public boolean isUsernameUnique(final OrganizerFormDTO organizer) {
        String generatedUsername = UsernameUtils.generateUsername(organizer.getUsername());
        Optional<User> userInDb = userRepository.findByUsername(generatedUsername);
        if (organizer.getUserId() == null) {
            return !userInDb.isPresent();
        } else {
            return !userInDb.isPresent()
                    || userInDb.get().getUserId().equals(organizer.getUserId());
        }
    }
}
