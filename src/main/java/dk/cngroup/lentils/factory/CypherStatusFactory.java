package dk.cngroup.lentils.factory;

import dk.cngroup.lentils.entity.CypherStatus;

public final class CypherStatusFactory {

    private CypherStatusFactory() {
        throw new IllegalStateException("Static factory cannot be instantiated");
    }

    public static CypherStatus create(final String newStatus) {
        switch (newStatus) {
            case "pending":
                return CypherStatus.PENDING;
            case "skip":
                return CypherStatus.SKIPPED;
            case "solve":
                return CypherStatus.SOLVED;
            case "lock":
                return CypherStatus.LOCKED;
            default:
                throw new IllegalArgumentException();
        }
    }

}
