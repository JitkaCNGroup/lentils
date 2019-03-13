package dk.cngroup.lentils.entity;

import java.io.Serializable;

public class StatusKey implements Serializable {
    private Long cypher;
    private Long team;

    public StatusKey() {
    }

    public StatusKey(final Long cypher, final Long team) {
        this.cypher = cypher;
        this.team = team;
    }

    public Long getCypher() {
        return cypher;
    }

    public Long getTeam() {
        return team;
    }
}
