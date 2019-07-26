package dk.cngroup.lentils.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class HintFormDTO {

    private String imageUrl;

    @NotEmpty(message = "Text nesmí být prázdný.")
    private String text;

    @Min(value = 1, message = "Hodnota musí být vetší než 0.")
    private int value;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }
}
