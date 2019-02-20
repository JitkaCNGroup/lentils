package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team add(Team team) {
        team.setPin(getUniquePin());
        return teamRepository.save(team);
    }

    public Optional<Team> get(Long id) {
        return teamRepository.findById(id);
    }

    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    public void deleteAll() {
        teamRepository.deleteAll();
    }

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    private String getUniquePin() {
        int pinLen = 4;
        String pin = getPIN(pinLen);

        while (!PINisUnique(pin)) {
            pin = getPIN(pinLen);
        }
        return pin;
    }

    private boolean PINisUnique(String pin) {
        List<Team> teamList = teamRepository.findAll();

        for (Team team : teamList) {
            if (team.getPin().equals(pin)) {
                return false;
            }
        }
        return true;
    }

    private String getPIN(int len) {
        String numbers = "0123456789";
        Random rnd = new Random();
        char[] pin = new char[len];
        for (int i = 0; i < len; i++) {
            pin[i] = numbers.charAt(rnd.nextInt(numbers.length()));
        }
        return String.copyValueOf(pin);
    }

    public void save(Team team) {
        teamRepository.save(team);
    }
}
