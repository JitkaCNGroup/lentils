package dk.cngroup.lentils.entity;

import java.io.Serializable;
import java.util.Objects;

public class StatusKey implements Serializable {
    private static final long serialVersionUID = 7422010369350370434L;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatusKey statusKey = (StatusKey) o;

        return cypher.equals(statusKey.cypher) &&
                team.equals(statusKey.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cypher, team);
    }
}
