package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TeamService {

    TeamRepository repository;

    @Autowired
    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public Team add(Team team) {
        team.setPin(getUniquePin());
        return repository.save(team);
    }

    public Optional<Team> get(long id) {
        return repository.findById(id);
    }
    public void delete(Team team) {
        repository.delete(team);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Team> getAll() {
        return repository.findAll();
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
        List<Team> teamList = repository.findAll();

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
}
