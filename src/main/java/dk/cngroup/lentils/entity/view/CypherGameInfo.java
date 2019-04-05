package dk.cngroup.lentils.entity.view;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "cypher_game_info")
public class CypherGameInfo {

    @Id
    @Column(name= "cypher_game_info_id")
    private Long cypherGameInfoId;

    @Column(name= "stage")
    private String stage;

    @Column(name= "name")
    private String name;

    @Column(name= "status")
    private int status;

    @Column(name= "count")
    private int count;

    public Long getCypherGameInfoId() {
        return cypherGameInfoId;
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
