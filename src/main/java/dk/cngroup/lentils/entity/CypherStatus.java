package dk.cngroup.lentils.entity;

public enum CypherStatus {
    SOLVED(10) {
        @Override
        public CypherStatus getNextCypherStatus() {
            return PENDING;
        }
    },
    SKIPPED(0) {
        @Override
        public CypherStatus getNextCypherStatus() {
            return PENDING;
        }
    },
    PENDING(0) {
        @Override
        public CypherStatus getNextCypherStatus() {
            return LOCKED;
        }
    },
    LOCKED(0) {
        @Override
        public CypherStatus getNextCypherStatus() {
            return LOCKED;
        }
    };

    private final int statusValue;

    CypherStatus(final int statusValue) {
        this.statusValue = statusValue;
    }

    public int getStatusValue() {
        return statusValue;
    }

    public abstract CypherStatus getNextCypherStatus();
}
