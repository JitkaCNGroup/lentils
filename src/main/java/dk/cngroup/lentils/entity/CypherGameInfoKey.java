package dk.cngroup.lentils.entity;

import java.io.Serializable;

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
}
