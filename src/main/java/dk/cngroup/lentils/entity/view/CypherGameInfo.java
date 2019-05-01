package dk.cngroup.lentils.entity.view;

import dk.cngroup.lentils.entity.CypherStatus;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect(
        "SELECT c.cypher_id as cypher_id, " +
                "c.stage as stage, " +
                "c.name as name, " +
                "s.cypher_status as status, " +
                "t.team_id as team_id, " +
                "count(ht.hint_hint_id) as count " +
        "FROM cypher c " +
        "CROSS JOIN team t " +
        "LEFT JOIN hint h on h.cypher_id = c.cypher_id " +
        "LEFT JOIN status s on s.cypher_cypher_id = c.cypher_id AND s.team_team_id = t.team_id " +
        "LEFT JOIN hint_taken ht on ht.hint_hint_id = h.hint_id AND ht.team_team_id = t.team_id " +
        "GROUP BY c.cypher_id, t.team_id, s.cypher_status " +
        "ORDER BY c.cypher_id"
)
public class CypherGameInfo {

    @Id
    @Column(name = "cypher_id")
    private Long cypherId;

    @Column(name = "stage")
    private String stage;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private CypherStatus status;

    @Column(name = "count")
    private int count;

    @Column(name = "team_id")
    private Long teamId;

    public Long getTeamId() {
        return teamId;
    }

    public Long getCypherId() {
        return cypherId;
    }

    public String getStage() {
        return stage;
    }

    public String getName() {
        return name; }

    public CypherStatus getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }
}
