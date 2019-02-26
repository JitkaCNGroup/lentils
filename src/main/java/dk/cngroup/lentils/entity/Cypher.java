package dk.cngroup.lentils.entity;

import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cypher")
public class Cypher implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "cypher_id")
    private Long cypherId;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cypher_id")
    private Set<Hint> hintsSet;

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

    public Cypher(Set<Hint> hintsSet, String name, int stage, Point location, String codeword) {
        this.hintsSet = hintsSet;
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

    public Long getCypherId() {
        return cypherId;
    }

    public void setCypherId(Long cypherId) {
        this.cypherId = cypherId;
    }

    public Set<Hint> getHintsSet() {
        return hintsSet;
    }

    public void setHintsSet(Set<Hint> hintsSet) {
        this.hintsSet = hintsSet;
    }

    @Override
    public String toString() {
        return "Cypher{" +
                "cypherId=" + cypherId +
                ", hintsSet=" + hintsSet +
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
        return stage == cypher.stage &&
                Objects.equals(cypherId, cypher.cypherId) &&
                Objects.equals(hintsSet, cypher.hintsSet) &&
                Objects.equals(name, cypher.name) &&
                Objects.equals(location, cypher.location) &&
                Objects.equals(codeword, cypher.codeword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cypherId, hintsSet, name, stage, location, codeword);
    }
}
