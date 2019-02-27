package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "hint")
public class Hint implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "hint_id")
    private Long hintId;

    @ManyToOne
    @JoinColumn(name = "cypher_id")
    Cypher cypher;

    @Column(name = "text")
    private String text;

    @Column(name = "value")
    private int value;

    public Hint() {
    }

    public Hint(String text, int value, Cypher cypher) {
        this.text = text;
        this.value = value;
    }

    public Cypher getCypher() {
        return cypher;
    }

    public void setCypher(Cypher cypher) {
        this.cypher = cypher;
    }

    public Long getHintId() {
        return hintId;
    }

    public void setHintId(Long hintId) {
        this.hintId = hintId;
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

    @Override
    public String toString() {
        return "Hint{" +
                "hintId=" + hintId +
                ", cypher=" + cypher +
                ", text='" + text + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hint hint = (Hint) o;
        return value == hint.value &&
                Objects.equals(hintId, hint.hintId) &&
                Objects.equals(cypher, hint.cypher) &&
                Objects.equals(text, hint.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hintId, cypher, text, value);
    }
}
