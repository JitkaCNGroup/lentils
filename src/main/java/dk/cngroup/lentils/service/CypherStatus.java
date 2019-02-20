package dk.cngroup.lentils.service;

public enum CypherStatus {
    SOLVED(10),
    SKIPPED(0),
    PENDING(0);

    private int statusValue;

    CypherStatus(int statusValue) {
        this.statusValue = statusValue;
    }

    public int getStatusValue() {
        return statusValue;
    }
}
