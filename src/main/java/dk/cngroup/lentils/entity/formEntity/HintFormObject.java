package dk.cngroup.lentils.entity.formEntity;

import dk.cngroup.lentils.entity.Cypher;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Component
public class HintFormObject implements Serializable {

    private Cypher cypher;

    @NotEmpty(message = "Text nesmí být prázdný.")
    private String text;

    @Nullable
    private String imageUrl;

    @Nullable
    private MultipartFile image;

    @Min(value = 1, message = "Hodnota musí být vetší než 0.")
    private int value;

    public HintFormObject() {
    }

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
