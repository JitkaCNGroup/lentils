package dk.cngroup.lentils.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ContactFormDTO {
    @NotEmpty(message = "Jméno nesmí být prázdné.")
    private String name;

    @NotEmpty(message = "Telefonní číslo nesmí být prázdné.")
    private String phoneNumber;

    @URL(message = "Neplatná URL adresa")
    private String webAddress;

    @URL(message = "Neplatná URL adresa")
    private String facebookEvent;

    @Email(message = "Neplatná emailová adresa")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(final String webAddress) {
        this.webAddress = webAddress;
    }

    public String getFacebookEvent() {
        return facebookEvent;
    }

    public void setFacebookEvent(final String facebookEvent) {
        this.facebookEvent = facebookEvent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
