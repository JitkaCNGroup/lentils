package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "num_of_members", length = 1)
    private int numOfMembers;

    @Column(name = "pin", nullable = false, length = 4, unique = true)
    private String pin;

    public Team() {
    }

    public Team(String name, int numOfMembers, String pin) {
        this.name = name;
        this.numOfMembers = numOfMembers;
        this.pin = pin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfMembers() {
        return numOfMembers;
    }

    public void setNumOfMembers(int numOfMembers) {
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
