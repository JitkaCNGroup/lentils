package dk.cngroup.lentils.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ContactFormDTO {
    @NotEmpty(message = "Jméno nesmí být prázdné.")
    private String name;

    @NotEmpty(message = "Telefonní číslo nesmí být prázdné.")
    private String phoneNumber;

    @Email(message = "Neplatná emailová adresa")
    private String email;

    @Valid
    private List<ContactLinkDTO> links;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public List<ContactLinkDTO> getLinks() {
        return links;
    }

    public void setLinks(final List<ContactLinkDTO> links) {
        this.links = links;
    }
}
