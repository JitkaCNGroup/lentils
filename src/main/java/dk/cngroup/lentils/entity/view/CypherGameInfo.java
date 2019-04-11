package dk.cngroup.lentils.entity.view;

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
                "count(h.hint_id) as count " +
        "FROM cypher c " +
        "LEFT JOIN hint h on h.cypher_id = c.cypher_id " +
        "LEFT JOIN status s on s.cypher_cypher_id = c.cypher_id " +
        "LEFT JOIN hint_taken ht on ht.hint_hint_id = h.hint_id " +
        "WHERE s.team_team_id = 2 " +
        "GROUP BY c.cypher_id " +
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
    private int status;

    @Column(name = "count")
    private int count;

    public Long getCypherId() {
        return cypherId;
    }

    public String getStage() {
        return stage;
    }

    public String getName() {
        return name; }

    public int getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }
}
