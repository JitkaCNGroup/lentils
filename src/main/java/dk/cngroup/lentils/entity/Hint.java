package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "hint")
public class Hint {
    @Column(name = "text")
    private String text;

    @Column(name = "value")
    private int value;

    @Id
    @GeneratedValue
    @Column(name = "hint_id")
    private Long hintId;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Cypher cypher;

    public Hint(String text, int value, Cypher cypher) {
        this.text = text;
        this.value = value;
        this.cypher = cypher;
    }

    public Long getHintId() {
        return hintId;
    }

    public void setHintId(Long hintId) {
        this.hintId = hintId;
    }

    public Cypher getCypher() {
        return cypher;
    }

    public void setCypher(Cypher cypher) {
        this.cypher = cypher;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setId(long id) {
        this.hintId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hint hint = (Hint) o;
        return value == hint.value &&
                Objects.equals(text, hint.text) &&
                Objects.equals(hintId, hint.hintId) &&
                Objects.equals(cypher, hint.cypher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, value, hintId, cypher);
    }

    @Override
    public String toString() {
        return "Hint{" +
                "text='" + text + '\'' +
                ", value=" + value +
                ", hintId=" + hintId +
                ", cypher=" + cypher +
                '}';
    }

}
