package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(HintTakenKey.class)
@Table(name = "hint_taken")
public class HintTaken implements Serializable {

    private static final long serialVersionUID = 3335818559381446168L;

    @Id
    @ManyToOne
    @JoinColumn(name = "team_team_id")
    private Team team;

    @Id
    @ManyToOne()
    @JoinColumn(name = "hint_hint_id")
    private Hint hint;

    public HintTaken() {
    }

    public HintTaken(final Team team, final Hint hint) {
        this.team = team;
        this.hint = hint;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(final Team team) {
        this.team = team;
    }

    public Hint getHint() {
        return hint;
    }

    public void setHint(final Hint hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {
        return "HintTaken{" +
                "team=" + team +
                ", hint=" + hint +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HintTaken hintTaken = (HintTaken) o;
        return Objects.equals(team, hintTaken.team) &&
                Objects.equals(hint, hintTaken.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, hint);
    }
}
