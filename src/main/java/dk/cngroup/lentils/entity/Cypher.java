package dk.cngroup.lentils.entity;

import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cypher")
public class Cypher implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "cypher_id")
    private Long cypherId;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "cypher_id")
    private List<Hint> hints;

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

    public Cypher(final int stage) {
        this.stage = stage;
    }

    public Cypher(final String name, final int stage, final Point location, final String codeword) {
        this.name = name;
        this.stage = stage;
        this.location = location;
        this.codeword = codeword;
    }

    public Cypher(
            final List<Hint> hints,
            final String name,
            final int stage,
            final Point location,
            final String codeword
    ) {
        this.hints = hints;
        this.name = name;
        this.stage = stage;
        this.location = location;
        this.codeword = codeword;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(final Point location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(final int stage) {
        this.stage = stage;
    }

    public String getCodeword() {
        return codeword;
    }

    public void setCodeword(final String codeword) {
        this.codeword = codeword;
    }

    public Long getCypherId() {
        return cypherId;
    }

    public void setCypherId(final Long cypherId) {
        this.cypherId = cypherId;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(final List<Hint> hints) {
        this.hints = hints;
    }

    public void addHint(final Hint hint) {
        hints.add(hint);
    }


    @Override
    public String toString() {
        return "Cypher{" +
                "cypherId=" + cypherId +
                ", hints=" + hints +
                ", name='" + name + '\'' +
                ", stage=" + stage +
                ", location=" + location +
                ", codeword='" + codeword + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cypher cypher = (Cypher) o;
        return stage == cypher.stage &&
                Objects.equals(cypherId, cypher.cypherId) &&
                Objects.equals(hints, cypher.hints) &&
                Objects.equals(name, cypher.name) &&
                Objects.equals(location, cypher.location) &&
                Objects.equals(codeword, cypher.codeword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cypherId, hints, name, stage, location, codeword);
    }


}
