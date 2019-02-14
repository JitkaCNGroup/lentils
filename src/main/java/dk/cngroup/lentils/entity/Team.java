package dk.cngroup.lentils.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    @NotEmpty
    private String name;

    @Column(name = "num_of_members", length = 1)
    @NotNull(message = "must not be empty")
    @Min(value = 1, message = "You must enter a number greater or equal to 1")
    @Max(value = 9, message = "You must enter a number less or equal to 9")
    private Integer numOfMembers;

    @Column(name = "pin", nullable = false, length = 4, unique = true)
    private String pin;

    @OneToMany(mappedBy = "team")
    Set<Progress> progressSet;

    public Team() {
    }

    public Team(String name, int numOfMembers, String pin) {
        this.name = name;
        this.numOfMembers = numOfMembers;
        this.pin = pin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public Integer getNumOfMembers() {
        return numOfMembers;
    }

    public void setNumOfMembers(Integer numOfMembers) {
        this.numOfMembers = numOfMembers;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numOfMembers=" + numOfMembers +
                ", pin='" + pin + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id &&
                numOfMembers == team.numOfMembers &&
                name.equals(team.name) &&
                pin.equals(team.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numOfMembers, pin);
    }
}
