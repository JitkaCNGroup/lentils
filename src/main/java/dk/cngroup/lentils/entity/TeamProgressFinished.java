package dk.cngroup.lentils.entity;

public class TeamProgressFinished implements TeamProgress {

    @Override
    public String getIdentifier() {
        return "finished";
    }

    @Override
    public String toString() {
        return "Hra ukončena";
    }
}
