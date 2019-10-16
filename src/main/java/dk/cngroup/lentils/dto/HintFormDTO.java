package dk.cngroup.lentils.dto;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.ImageSource;
import dk.cngroup.lentils.service.validators.MandatoryFieldsDependentOnOther;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@MandatoryFieldsDependentOnOther(baseField = "imageSource", matchField1 = "imageFile",
        matchField2 = "imageUrl", matchField3 = "filename")
public class HintFormDTO implements Serializable {

    private Cypher cypher;

    @NotEmpty(message = "Text nesmí být prázdný.")
    private String text;

    private String imageUrl;

    private String filename;

    private MultipartFile imageFile;

    private ImageSource imageSource;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(final MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public ImageSource getImageSource() {
        return imageSource;
    }

    public void setImageSource(final ImageSource imageSource) {
        this.imageSource = imageSource;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }
}
