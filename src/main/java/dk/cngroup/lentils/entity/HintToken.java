package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HintToken  implements Serializable {

    @Column(name = "cypher_id")
    long cypherId;

    @Column(name = "team_id")
    long teamId;

    @Column(name = "hint_id")
    long hintId;

    public HintToken() {
    }

    public HintToken(long cypherId, long teamId, long hintId) {
        this.cypherId = cypherId;
        this.teamId = teamId;
        this.hintId = hintId;
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

    public long getHintId() {
        return hintId;
    }

    public void setHintId(long hintId) {
        this.hintId = hintId;
    }

    @Override
    public String toString() {
        return "HintToken{" +
                "cypherId=" + cypherId +
                ", teamId=" + teamId +
                ", hintId=" + hintId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HintToken hintToken = (HintToken) o;
        return cypherId == hintToken.cypherId &&
                teamId == hintToken.teamId &&
                hintId == hintToken.hintId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cypherId, teamId, hintId);
    }
}
