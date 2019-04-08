package dk.cngroup.lentils.entity.view;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect(
        "select Cypher.cypher_Id as cypher_id, " +
                "Cypher.stage as stage, " +
                "Cypher.name as name, " +
                "Status.cypher_Status as status, " +
                "count(Hint.hint_Id) as count " +
                "from Cypher " +
                "left join Hint on Hint.cypher_Id = Cypher.cypher_Id " +
                "left join Status on Status.cypher_cypher_Id = Cypher.cypher_Id " +
                "left join Hint_Taken on Hint_Taken.hint_hint_Id = Hint.hint_Id " +
                "where Status.team_team_Id = 2 " +
                "group by Cypher.cypher_Id " +
                "order by Cypher.cypher_Id"
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
