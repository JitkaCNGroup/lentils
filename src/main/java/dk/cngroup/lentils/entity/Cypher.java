package dk.cngroup.lentils.entity;

import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cypher")
public class Cypher {

    @OneToMany(mappedBy = "cypher")
    private Set<Status> statusSet;

    @OneToMany(mappedBy = "cypher")
    private Set<Hint> hintSet;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "stage", nullable = false)
    private int stage;

    @Column(name = "location")
    private Point location;

    @Column(name = "codeword")
    private String codeword;

    public Cypher() {
    }

    public Cypher(int stage) {
        this.stage = stage;
    }

    public Cypher(String name, int stage, Point location, String codeword) {
        this.name = name;
        this.stage = stage;
        this.location = location;
        this.codeword = codeword;
    }

    public Cypher(Set<Status> statusSet, Set<Hint> hintSet, String name, int stage, Point location, String codeword) {
        this.statusSet = statusSet;
        this.hintSet = hintSet;
        this.name = name;
        this.stage = stage;
        this.location = location;
        this.codeword = codeword;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getCodeword() {
        return codeword;
    }

    public void setCodeword(String codeword) {
        this.codeword = codeword;
    }

    public Set<Status> getStatusSet() {
        return statusSet;
    }

    public void setStatusSet(Set<Status> statusSet) {
        this.statusSet = statusSet;
    }

    public Set<Hint> getHintSet() {
        return hintSet;
    }

    public void setHintSet(Set<Hint> hintSet) {
        this.hintSet = hintSet;
    }

    @Override
    public String toString() {
        return "Cypher{" +
                "statusSet=" + statusSet +
                ", hintSet=" + hintSet +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", stage=" + stage +
                ", location=" + location +
                ", codeword='" + codeword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cypher cypher = (Cypher) o;
        return id == cypher.id &&
                stage == cypher.stage &&
                Objects.equals(statusSet, cypher.statusSet) &&
                Objects.equals(hintSet, cypher.hintSet) &&
                Objects.equals(name, cypher.name) &&
                Objects.equals(location, cypher.location) &&
                Objects.equals(codeword, cypher.codeword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusSet, hintSet, id, name, stage, location, codeword);
    }

}
