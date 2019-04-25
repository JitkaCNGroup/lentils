package dk.cngroup.lentils.entity;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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

    @Column(name = "web-pages")
    private String webPages;

    @URL
    @Column(name = "fcb_event")
    private String fcbEvent;

    public Contact() {
    }

    public Contact(final String name,
                   final String phoneNumber,
                   final String webPages,
                   final String fcbEvent) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.webPages = webPages;
        this.fcbEvent = fcbEvent;
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

    public String getWebPages() {
        return webPages;
    }

    public void setWebPages(final String webPages) {
        this.webPages = webPages;
    }

    public String getFcbEvent() {
        return fcbEvent;
    }

    public void setFcbEvent(final String fcbEvent) {
        this.fcbEvent = fcbEvent;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", webPages='" + webPages + '\'' +
                ", fcbEvent='" + fcbEvent + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(getContactId(), contact.getContactId()) &&
                Objects.equals(getName(), contact.getName()) &&
                Objects.equals(getPhoneNumber(), contact.getPhoneNumber()) &&
                Objects.equals(getWebPages(), contact.getWebPages()) &&
                Objects.equals(getFcbEvent(), contact.getFcbEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContactId(), getName(), getPhoneNumber(), getWebPages(), getFcbEvent());
    }
}
