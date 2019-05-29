package dk.cngroup.lentils.entity;

public class TeamProgressNotStarted implements TeamProgress {

    @Override
    public String getIdentifier() {
        return "not-started";
    }

    @Override
    public String toString() {
        return "Hra nezahájena";
    }
}
