package dk.cngroup.lentils.entity;

import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cypher")
public class Cypher {

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

    @Column(name = "hint")
    private String hint;

    @OneToMany(mappedBy = "cypher")
    Set<Progress> progressSet;

    public Cypher() {
    }

    public Cypher(int stage) {
        this.stage = stage;
    }

    public Cypher(String name, int stage, Point location, String codeword, String hint) {
        this.name = name;
        this.stage = stage;
        this.location = location;
        this.codeword = codeword;
        this.hint = hint;
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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Set<Progress> getProgressSet() {
        return progressSet;
    }

    public void setProgressSet(Set<Progress> progressSet) {
        this.progressSet = progressSet;
    }

    @Override
    public String toString() {
        return "Cypher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stage=" + stage +
                ", location=" + location +
                ", codeword='" + codeword + '\'' +
                ", hint='" + hint + '\'' +
                ", progressSet=" + progressSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cypher cypher = (Cypher) o;
        return id == cypher.id &&
                stage == cypher.stage &&
                Objects.equals(name, cypher.name) &&
                Objects.equals(location, cypher.location) &&
                Objects.equals(codeword, cypher.codeword) &&
                Objects.equals(hint, cypher.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stage, location, codeword, hint);
    }
}
