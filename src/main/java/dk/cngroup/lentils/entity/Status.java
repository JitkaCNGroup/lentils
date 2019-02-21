package dk.cngroup.lentils.entity;

import dk.cngroup.lentils.service.CypherStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "progress")
public class Progress {
    @EmbeddedId
    ProgressKey id;

    @ManyToOne
    @MapsId("team_id")
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne
    @MapsId("cypher_id")
    @JoinColumn(name = "cypher_id")
    Cypher cypher;

    private CypherStatus cypherStatus;

    public Progress() {
    }

    public Progress(ProgressKey id, Team team, Cypher cypher) {
        this.id = id;
        this.team = team;
        this.cypher = cypher;
        this.cypherStatus = CypherStatus.SOLVED;
    }

    public Progress(ProgressKey id, Team team, Cypher cypher, CypherStatus cypherStatus) {
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

    public ProgressKey getId() {
        return id;
    }

    public void setId(ProgressKey id) {
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
        Progress progress = (Progress) o;
        return id.equals(progress.id) &&
                team.equals(progress.team) &&
                cypher.equals(progress.cypher) &&
                cypherStatus == progress.cypherStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, team, cypher, cypherStatus);
    }
}
