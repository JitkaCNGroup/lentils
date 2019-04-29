package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Contact;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.FinalPlace;
import dk.cngroup.lentils.entity.Hint;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ObjectGenerator {

    public static final int NUMBER_OF_TEAMS = 10;
    public static final int NUMBER_OF_CYPHERS = 5;
    public static final int NUMBER_OF_HINTS_FOR_CYPHER = 3;
    public static final Point TEST_LOCATION = new Point(59.9090442, 10.7423389);
    public static final String CODEWORD = "Codeword";
    public static final String TEAM_NAME = "Team";
    public static final String PIN = "1234";

    public List<Cypher> generateCypherList(int number) {
        List<Cypher> cyphers = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            cyphers.add(new Cypher(TEST_LOCATION, i + number));
        }
        return cyphers;
    }

    public List<Cypher> generateCypherList() {
        return generateCypherList(NUMBER_OF_CYPHERS);
    }

    public Cypher generateNewCypher() {
        return new Cypher(TEST_LOCATION, 1);
    }
/*
    public Cypher generateCypher() {
        return new Cypher("Easy", TESTED_STAGE, new Point(49.0988161, 17.7519189), CODEWORD);
    }
*/
    public List<Team> generateTeamList() {
        List<Team> teams = new LinkedList<>();
        IntStream.range(0, NUMBER_OF_TEAMS).forEach(i -> {
            teams.add(new Team(TEAM_NAME + i, 5, "123" + i));
        });
        return teams;
    }

    public Team generateNewTeam() {
        return new Team(TEAM_NAME, 5, PIN);
    }

    public List<Hint> generateHintsForCypher(List<Cypher> cyphers) {
        List<Hint> hints = new LinkedList<>();
        for (Cypher cypher : cyphers) {
            for (int i = 0; i < NUMBER_OF_HINTS_FOR_CYPHER; i++) {
                hints.add(new Hint("text", i+1, cypher));
            }
        }
        return hints;
    }

    public List<Hint> generateHintsForCypher(Cypher cypher) {

        List<Cypher> cyphers = new LinkedList<>();
        cyphers.add(cypher);

        return generateHintsForCypher(cyphers);
    }

    public FinalPlace generateFinalPlace() {
        return new FinalPlace("konecna stanice - krematorium", new Point(2.123, 3.456),
                LocalDateTime.now());
    }

    public User createUser(final String username) {
        final User user = new User();

        user.setUsername(username);
        user.setPassword("1234");

        return user;
    }

    public Contact generateContact() {
        return new Contact("Veru", "123 456 789", "www.cngroup.dk", "http://xxx");
    }
}
