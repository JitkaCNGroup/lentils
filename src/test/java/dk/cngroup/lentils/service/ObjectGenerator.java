package dk.cngroup.lentils.service;

import dk.cngroup.lentils.entity.Cypher;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ObjectGenerator
{

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


}
