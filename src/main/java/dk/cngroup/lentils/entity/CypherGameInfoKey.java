package dk.cngroup.lentils.entity;

import java.io.Serializable;
import java.util.Objects;

public class CypherGameInfoKey implements Serializable {
    private Long cypherId;
    private Long teamId;

    public CypherGameInfoKey() {
    }

    public CypherGameInfoKey(final Long cypherId, final Long teamId) {
        this.cypherId = cypherId;
        this.teamId = teamId;
    }

    public Long getCypherId() {
        return cypherId;
    }

    public Long getTeamId() {
        return teamId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CypherGameInfoKey that = (CypherGameInfoKey) o;

        return cypherId.equals(that.cypherId) &&
                teamId.equals(that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cypherId, teamId);
    }
}
