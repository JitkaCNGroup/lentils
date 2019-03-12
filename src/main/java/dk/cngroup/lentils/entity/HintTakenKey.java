package dk.cngroup.lentils.entity;

import java.io.Serializable;
import java.util.Objects;

public class HintTakenKey implements Serializable {
    private Long team;
    private Long hint;

    public HintTakenKey() {
    }

    public HintTakenKey(final Long teamId, final Long hintId) {
        this.team = teamId;
        this.hint = hintId;
    }

    public Long getTeam() {
        return team;
    }

    public Long getHint() {
        return hint;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HintTakenKey that = (HintTakenKey) o;
        return Objects.equals(team, that.team) &&
                Objects.equals(hint, that.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, hint);
    }
}
