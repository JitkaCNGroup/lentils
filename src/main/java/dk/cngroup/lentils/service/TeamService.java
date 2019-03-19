package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.exception.ResourceNotFoundException;
import dk.cngroup.lentils.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TeamService {

    private static final int PIN_LENGTH = 4;
    private static final String PIN_CHARACTERS = "0123456789";

    private final TeamRepository teamRepository;
    private final UserService userService;

    @Autowired
    public TeamService(final TeamRepository teamRepository,
                       final UserService userService) {
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    public Team save(final Team team) {
        team.setPin(getUniquePin());
        userService.createUserForTeam(team);
        return teamRepository.save(team);
    }

    public Team getTeam(final Long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.get();
        }
        throw new ResourceNotFoundException(Team.class.getSimpleName(), id);
    }

    public void delete(final Long id) {
        teamRepository.deleteById(id);
    }

    public void deleteAll() {
        teamRepository.deleteAll();
    }

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    private String getUniquePin() {
        String pin = getPIN(PIN_LENGTH);

        while (!isPINUnique(pin)) {
            pin = getPIN(PIN_LENGTH);
        }
        return pin;
    }

    private boolean isPINUnique(final String pin) {
        List<Team> teamList = teamRepository.findAll();

        for (Team team : teamList) {
            if (team.getPin().equals(pin)) {
                return false;
            }
        }
        return true;
    }

    private String getPIN(final int len) {
        String numbers = PIN_CHARACTERS;
        Random rnd = new Random();
        char[] pin = new char[len];
        for (int i = 0; i < len; i++) {
            pin[i] = numbers.charAt(rnd.nextInt(numbers.length()));
        }
        return String.copyValueOf(pin);
    }
}
