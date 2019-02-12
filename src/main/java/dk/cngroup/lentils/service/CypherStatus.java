package dk.cngroup.lentils.service;

public enum CypherStatus {
    SOLVED(10),
    SOLVED_WITH_HINT(5),
    SKIPPED(-10),
    PENDING(0);

    private int statusValue;

    CypherStatus(int s) {
    }

    public int getStatusValue() {
        return statusValue;
    }
}
