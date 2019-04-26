package dk.cngroup.lentils.exception;

public class NextCypherNotFoundException extends RuntimeException {
    public NextCypherNotFoundException() {
        super("Next cypher was not found.");
    }
}
