package dk.cngroup.lentils.dto;

import dk.cngroup.lentils.entity.Cypher;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class HintFormDTO implements Serializable {

    private Cypher cypher;

    @NotEmpty(message = "Text nesmí být prázdný.")
    private String text;

    private String imageUrl;

    private MultipartFile image;

    @Min(value = 1, message = "Hodnota musí být vetší než 0.")
    private int value;

    public Cypher getCypher() {
        return cypher;
    }

    public void setCypher(final Cypher cypher) {
        this.cypher = cypher;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(final MultipartFile image) {
        this.image = image;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }
}
