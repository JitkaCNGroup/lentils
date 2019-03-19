package dk.cngroup.lentils.entity.view;

import jdk.nashorn.internal.ir.annotations.Immutable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "CYPHER_GAME_INFO")
public class CypherGameInfo {

    @Id
    private Long ID;

    private String stage;

    private String name;

    private int status;

    private int count;

    public Long getID() {
        return ID;
    }

    public String getStage() {
        return stage;
    }

    public String getName() { return name; }

    public int getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }
}
