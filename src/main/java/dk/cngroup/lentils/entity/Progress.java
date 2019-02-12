package dk.cngroup.lentils.entity;

import dk.cngroup.lentils.service.CypherStatus;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "progress")
public class Progress{

    @EmbeddedId
    id id
            
    @Id
    @Column(name = "team_id")
    long teamId;

    @Id
    @Column(name = "cypher_id")
    long cypherId;

    @ManyToMany(mappedBy = "projects")
    @JoinTable(name = "cypher", joinColumns = "id", foreignKey = "cypher_id")
    private List<Cypher> cyphers;

    @Column(name = "cypher_status")
    CypherStatus cypherStatus;

    public Progress() {
    }

    public CypherStatus getCypherStatus() {
        return cypherStatus;
    }

    public void setCypherStatus(CypherStatus cypherStatus) {
        this.cypherStatus = cypherStatus;
    }
}
