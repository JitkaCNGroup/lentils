package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HintTakenKey.class)
@Table(name = "hint_taken")
public class HintTaken {

    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn
    private Team team;

    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn
    private Hint hint;

    public HintTaken() {
    }

    public HintTaken(Team team, Hint hint) {
        this.team = team;
        this.hint = hint;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Hint getHint() {
        return hint;
    }

    public void setHint(Hint hint) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HintTaken hintTaken = (HintTaken) o;
        return Objects.equals(team, hintTaken.team) &&
                Objects.equals(hint, hintTaken.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, hint);
    }
}
