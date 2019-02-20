package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.FinalPlace;
import org.springframework.data.geo.Point;
import dk.cngroup.lentils.entity.Team;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ObjectGenerator {

    public static final int NUMBER_OF_TEAMS = 10;

    public static final int NUMBER_OF_CYPHERS = 5;

    public static final int TESTED_STAGE = 3;

    public static final int TESTED_TEAM = 7;

    public static final String CODEWORD = "Codeword";

    public static final String TEAM_NAME = "Team";

    public List<Cypher> generateCypherList(int number) {
        List<Cypher> cyphers = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            cyphers.add(new Cypher(i % number));
        }
        return cyphers;
    }

    public List<Cypher> generateCypherList() {
        return generateCypherList(NUMBER_OF_CYPHERS);
    }

    public Set<Cypher> generateCypherSet(int number) {
        return new HashSet<>(generateCypherList(number));
    }

    public Cypher generateCypher() {
        return new Cypher("Easy", TESTED_STAGE, new Point(49.0988161, 17.7519189), CODEWORD, "dole");
    }

    public Team generateTeam() {
        return new Team(TEAM_NAME + TESTED_TEAM, 5, "1234");
    }

    public List<Team> generateTeamList() {
        List<Team> teams = new LinkedList<>();
        for (int i = 0; i < NUMBER_OF_TEAMS; i++) {
            teams.add(new Team((long) (10 * i), "TEAM_NAME" + i, i + 1, "123" + i));
        }
        return teams;
    }

    public FinalPlace generateFinalPlace() {
        return new FinalPlace("konecna stanice - krematorium", new Point(2.123, 3.456),
                LocalDateTime.now());
    }
}
