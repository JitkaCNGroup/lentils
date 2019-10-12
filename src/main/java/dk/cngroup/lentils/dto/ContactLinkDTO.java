package dk.cngroup.lentils.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ContactLinkDTO {
    private Long id;

    @NotEmpty
    @Size(max = 255)
    private String title;

    @NotEmpty
    @URL(message = "Neplatná URL adresa")
    @Size(max = 2048)
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
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
