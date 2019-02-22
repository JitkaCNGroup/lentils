package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "hint_taken")
public class HintTaken {
    @EmbeddedId
    HintTakenKey id;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne
    @MapsId("hint_id")
    @JoinColumn(name = "hint_id")
    Hint hint;

    public HintTaken() {
    }

    public HintTaken(HintTakenKey id, Team team, Hint hint) {
        this.id = id;
        this.team = team;
        this.hint = hint;
    }

    public HintTaken(HintTakenKey id) {
        this.id = id;
    }

    public HintTakenKey getId() {
        return id;
    }

    public void setId(HintTakenKey id) {
        this.id = id;
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
                "id=" + id +
                ", team=" + team +
                ", hint=" + hint +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HintTaken hintTaken = (HintTaken) o;
        return Objects.equals(id, hintTaken.id) &&
                Objects.equals(team, hintTaken.team) &&
                Objects.equals(hint, hintTaken.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, team, hint);
    }
}
