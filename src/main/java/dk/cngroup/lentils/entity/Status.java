package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(StatusKey.class)
@Table(name = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 2698218140068854086L;

    @Id
    @ManyToOne
    private Team team;

    @Id
    @ManyToOne
    private Cypher cypher;

    @Enumerated(EnumType.ORDINAL)
    private CypherStatus cypherStatus;

    public Status() {
    }

    public Status(
            final Team team,
            final Cypher cypher,
            final CypherStatus cypherStatus
    ) {
        this.team = team;
        this.cypher = cypher;
        this.cypherStatus = cypherStatus;
    }

    public CypherStatus getCypherStatus() {
        return cypherStatus;
    }

    public void setCypherStatus(final CypherStatus cypherStatus) {
        this.cypherStatus = cypherStatus;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(final Team team) {
        this.team = team;
    }

    public Cypher getCypher() {
        return cypher;
    }

    public void setCypher(final Cypher cypher) {
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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
