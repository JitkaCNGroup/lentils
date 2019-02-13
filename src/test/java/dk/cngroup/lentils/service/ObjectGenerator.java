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

	public List<Cypher> generateCypherList(int number) {
		List<Cypher> cyphers = new LinkedList<>();
		for (int i = 0; i < number; i++) {
			cyphers.add(new Cypher(i % number));
		}
		return cyphers;
	}

	public Set<Cypher> generateCypherSet(int number) {
		return new HashSet<>(generateCypherList(number));
	}

	public Team generateTeam() {
		return new Team("Kocka", 5, "1234");
	}

	public List<Team> generateTeamList() {
		List<Team> teams = new LinkedList<>();
		for (int i = 0; i < NUMBER_OF_TEAMS; i++) {
			teams.add(new Team("kocky" + i, i, "123" + i));
		}
		return teams;
	}

	public FinalPlace generateFinalPlace() {
		return new FinalPlace("konecna stanice - krematorium", new Point(2.123, 3.456),
				LocalDateTime.now());
	}
}
