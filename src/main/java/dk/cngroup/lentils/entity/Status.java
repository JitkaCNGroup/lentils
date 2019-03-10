package dk.cngroup.lentils.entity;

import dk.cngroup.lentils.service.CypherStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(StatusKey.class)
@Table(name = "status")
public class Status {

    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn
    private Team team;

    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cypher cypher;

    @Enumerated(EnumType.ORDINAL)
    private CypherStatus cypherStatus;

    public Status() {
    }

    public Status(Team team, Cypher cypher, CypherStatus cypherStatus) {
        this.team = team;
        this.cypher = cypher;
        this.cypherStatus = cypherStatus;
    }

    public CypherStatus getCypherStatus() {
        return cypherStatus;
    }

    public void setCypherStatus(CypherStatus cypherStatus) {
        this.cypherStatus = cypherStatus;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Cypher getCypher() {
        return cypher;
    }

    public void setCypher(Cypher cypher) {
        this.cypher = cypher;
    }

    @Override
    public String toString() {
        return "Status{" +
                "team=" + team +
                ", cypher=" + cypher +
                ", cypherStatus=" + cypherStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(team, status.team) &&
                Objects.equals(cypher, status.cypher) &&
                cypherStatus == status.cypherStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, cypher, cypherStatus);
    }
}
