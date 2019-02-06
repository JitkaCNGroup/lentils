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

	public List<Cypher> generateDeviceList(int number, String prefix) {
		List<Cypher> devices = new LinkedList<>();
		for (int i = 0; i < number; i++) {
			devices.add(new Cypher());
		}
		return devices;
	}

	public Set<Cypher> generateCypherSet(int number, String prefix) {
		return new HashSet<>(generateDeviceList(number, prefix));
	}


}
