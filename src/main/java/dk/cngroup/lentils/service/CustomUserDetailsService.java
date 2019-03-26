package dk.cngroup.lentils.service;

import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Uživatel neexistuje."));
        return optionalUser.map(CustomUserDetails::new).get();
    }
}
