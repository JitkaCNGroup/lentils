package dk.cngroup.lentils.logger.change;

import dk.cngroup.lentils.entity.CypherStatus;

public class StatusChange {
    private final Long cypherId;
    private final CypherStatus oldCypherStatus;
    private final CypherStatus newCypherStatus;

    public StatusChange(final Long cypherId,
                        final CypherStatus oldCypherStatus,
                        final CypherStatus newCypherStatus) {
        this.cypherId = cypherId;
        this.oldCypherStatus = oldCypherStatus;
        this.newCypherStatus = newCypherStatus;
    }

    @Override
    public String toString() {
        return "StatusChange{" +
                "cypherId=" + cypherId +
                ", oldCypherStatus=" + oldCypherStatus +
                ", newCypherStatus=" + newCypherStatus +
                '}';
    }
}
