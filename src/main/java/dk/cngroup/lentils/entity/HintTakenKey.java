package dk.cngroup.lentils.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HintTakenKey implements Serializable {

    @Column(name = "hint_id")
    long hintId;

    @Column(name = "team_id")
    long teamId;

    public HintTakenKey() {
    }

    public HintTakenKey(long teamId, long hintId ) {
        this.hintId = hintId;
        this.teamId = teamId;
    }

    public long getHintId() {
        return hintId;
    }

    public void setHintId(long hintId) {
        this.hintId = hintId;
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
        HintTakenKey that = (HintTakenKey) o;
        return hintId == that.hintId &&
                teamId == that.teamId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hintId, teamId);
    }
}
