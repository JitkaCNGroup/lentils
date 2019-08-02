package dk.cngroup.lentils.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "hint")
public class Hint implements Serializable {

    private static final long serialVersionUID = -4213650803695289629L;

    @Id
    @GeneratedValue
    @Column(name = "hint_id")
    private Long hintId;

    @ManyToOne()
    @JoinColumn(name = "cypher_id")
    private Cypher cypher;

    @Column(name = "text")
    @NotEmpty(message = "Text nesmí být prázdný.")
    private String text;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "value")
    @Min(value = 1, message = "Hodnota musí být vetší než 0.")
    private int value;

    public Hint() {
    }

    public Hint(final Cypher cypher) {
        this.cypher = cypher;
    }

    public Hint(final String text, final int value, final Cypher cypher) {
        this.text = text;
        this.value = value;
        this.cypher = cypher;
    }

    public Long getCypherId() {
        return cypher.getCypherId();
    }

    public Cypher getCypher() {
        return cypher;
    }

    public void setCypher(final Cypher cypher) {
        this.cypher = cypher;
    }

    public Long getHintId() {
        return hintId;
    }

    public void setHintId(final Long hintId) {
        this.hintId = hintId;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Hint{" +
                "hintId=" + hintId +
                ", cypherId=" + cypher.getCypherId() +
                ", text='" + text + '\'' +
                ", value=" + value +
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
