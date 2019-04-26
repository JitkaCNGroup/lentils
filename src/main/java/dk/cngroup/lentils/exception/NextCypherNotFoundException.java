package dk.cngroup.lentils.exception;

public class NextCypherNotFoundException extends RuntimeException{
    public NextCypherNotFoundException() {
        super("Status of the last cypher has been changed");
    }
}
