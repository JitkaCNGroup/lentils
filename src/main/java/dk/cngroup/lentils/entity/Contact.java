package dk.cngroup.lentils.entity;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue
    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "name")
    @NotEmpty(message = "Jméno nesmí být prázdné.")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "web_pages")
    private String webAddress;

    @URL
    @Column(name = "fcb_event")
    private String facebookEvent;

    @Email
    @Column(name = "email")
    private String email;

    public Contact() {
    }

    public Contact(final String name,
                   final String phoneNumber,
                   final String webAddress,
                   final String facebookEvent,
                   final String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.webAddress = webAddress;
        this.facebookEvent = facebookEvent;
        this.email = email;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(final Long contactId) {
        this.contactId = contactId;
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", webAddress='" + webAddress + '\'' +
                ", facebookEvent='" + facebookEvent + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(contactId, contact.contactId) &&
                Objects.equals(name, contact.name) &&
                Objects.equals(phoneNumber, contact.phoneNumber) &&
                Objects.equals(webAddress, contact.webAddress) &&
                Objects.equals(facebookEvent, contact.facebookEvent) &&
                Objects.equals(email, contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId, name, phoneNumber, webAddress, facebookEvent, email);
    }
}
