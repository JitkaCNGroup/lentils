package dk.cngroup.lentils.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "imageUrl")
    @NotEmpty(message = "Musí být zadaná cesta k obrázku")
    private String imageUrl;

    @Column(name = "isFromFile")
    private boolean isFromFile;

    public Image() {
    }

    public Image(final String imageUrl, final boolean isFromFile) {
        this.imageUrl = imageUrl;
        this.isFromFile = isFromFile;
    }

    public Image(final String imageUrl) {
        this.imageUrl = imageUrl;
        this.isFromFile = true;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(final Long imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFromFile() {
        return isFromFile;
    }

    public void setFromFile(final boolean fromFile) {
        isFromFile = fromFile;
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + imageUrl + '\'' +
                '}';
    }
}
