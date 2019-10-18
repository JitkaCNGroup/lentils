package dk.cngroup.lentils.entity;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class ContactLink {
    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    @Size(max = 255)
    private String title;

    @NotEmpty
    @URL(message = "Neplatná URL adresa")
    @Size(max = 2048)
    private String url;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
