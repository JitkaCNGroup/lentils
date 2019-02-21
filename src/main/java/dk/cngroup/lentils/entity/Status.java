package dk.cngroup.lentils.entity;

import dk.cngroup.lentils.service.CypherStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "progress")
public class Status {
    @EmbeddedId
    StatusKey id;

    @ManyToOne
    @MapsId("team_id")
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne
    @MapsId("cypher_id")
    @JoinColumn(name = "cypher_id")
    Cypher cypher;

    private CypherStatus cypherStatus;

    public Status() {
    }

    public Status(StatusKey id, Team team, Cypher cypher) {
        this.id = id;
        this.team = team;
        this.cypher = cypher;
        this.cypherStatus = CypherStatus.SOLVED;
    }

    public Status(StatusKey id, Team team, Cypher cypher, CypherStatus cypherStatus) {
        this.id = id;
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

    public StatusKey getId() {
        return id;
    }

    public void setId(StatusKey id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return id.equals(status.id) &&
                team.equals(status.team) &&
                cypher.equals(status.cypher) &&
                cypherStatus == status.cypherStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, team, cypher, cypherStatus);
    }
}
