package dk.cngroup.lentils.entity;

import javax.persistence.*;
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
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "name", nullable = false, length = 50)
    @NotEmpty
    private String name;

    @Column(name = "num_of_members", length = 1)
    @NotNull(message = "must not be empty")
    @Min(value = 1, message = "numOfMembers - You must enter a number greater or equal to 1")
    private Integer numOfMembers;

    @Column(name = "pin", nullable = false, unique = true)
    private String pin;

    public Team() {
    }

    public Team(String name, int numOfMembers, String pin) {
        this.name = name;
        this.numOfMembers = numOfMembers;
        this.pin = pin;
    }

    public Team(@NotEmpty String name, @NotNull(message = "must not be empty") @Min(value = 1, message =
            "numOfMembers - You must enter a number greater or equal to 1") Integer numOfMembers) {
        this.name = name;
        this.numOfMembers = numOfMembers;
    }

    public Team(Long teamId, String name, int numOfMembers, String pin) {
        this.teamId = teamId;
        this.name = name;
        this.numOfMembers = numOfMembers;
        this.pin = pin;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
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
                "teamId=" + teamId +
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
        return Objects.equals(teamId, team.teamId) &&
                Objects.equals(name, team.name) &&
                Objects.equals(numOfMembers, team.numOfMembers) &&
                Objects.equals(pin, team.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, name, numOfMembers, pin);
    }
}
