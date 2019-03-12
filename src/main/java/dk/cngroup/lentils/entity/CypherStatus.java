package dk.cngroup.lentils.entity;

public enum CypherStatus {
    SOLVED(10),
    SKIPPED(0),
    PENDING(0);

    private int statusValue;

    CypherStatus(final int statusValue) {
        this.statusValue = statusValue;
    }

    public int getStatusValue() {
        return statusValue;
    }
}
