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

    @Column(name = "image_url")
    @NotEmpty(message = "Musí být zadaná cesta k obrázku")
    private String imageUrl;

    @Column(name = "local")
    private boolean local;

    public Image() {
    }

    public Image(final String imageUrl, final boolean local) {
        this.imageUrl = imageUrl;
        this.local = local;
    }

    public Image(final String imageUrl) {
        this.imageUrl = imageUrl;
        this.local = true;
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

    public boolean isLocal() {
        return local;
    }

    public void setLocal(final boolean local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + imageUrl + '\'' +
                '}';
    }
}
