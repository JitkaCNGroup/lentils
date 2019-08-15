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

    @Column(name = "path")
    @NotEmpty(message = "Musí být zadaná cesta k souboru")
    private String path;

    public Image() {
    }

    public Image(final String path) {
        this.path = path;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(final Long imageId) {
        this.imageId = imageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                '}';
    }
}
