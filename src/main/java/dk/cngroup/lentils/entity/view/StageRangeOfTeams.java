package dk.cngroup.lentils.entity.view;

public class StageRangeOfTeams {
    private final int min;
    private final int max;

    public StageRangeOfTeams(final int min, final int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}