package dk.cngroup.lentils.exception;

import javassist.NotFoundException;

public class TeamNotFoundException extends NotFoundException {
    public TeamNotFoundException(Long id) {
        super("Team with id :" + id + " not found.");
    }
}
