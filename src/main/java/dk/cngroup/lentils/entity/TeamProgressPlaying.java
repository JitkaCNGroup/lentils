package dk.cngroup.lentils.entity;

public class TeamProgressPlaying implements TeamProgress {

    private int actualStage;

    public TeamProgressPlaying(final int actualStage) {
        this.actualStage = actualStage;
    }

    @Override
    public String toString() {
        return "Aktuální stage: " + actualStage;
    }
}
