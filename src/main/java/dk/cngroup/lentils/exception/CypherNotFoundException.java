package dk.cngroup.lentils.exception;

import javassist.NotFoundException;

public class CypherNotFoundException extends NotFoundException {
    public CypherNotFoundException(Long id) {
        super("Cypher with Id "+ id + " not found.");
    }
}
