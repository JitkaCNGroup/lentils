package dk.cngroup.lentils.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProgressKey implements Serializable {

    @Column(name = "cypher_id")
    long cypherId;

    @Column(name = "team_id")
    long teamId;

    public ProgressKey() {
    }

    public ProgressKey(long cypherId, long teamId) {
        this.cypherId = cypherId;
        this.teamId = teamId;
    }

    public long getCypherId() {
        return cypherId;
    }

    public void setCypherId(long cypherId) {
        this.cypherId = cypherId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgressKey that = (ProgressKey) o;
        return cypherId == that.cypherId &&
                teamId == that.teamId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cypherId, teamId);
    }
}
