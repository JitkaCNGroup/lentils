package dk.cngroup.lentils.entity;

import org.springframework.data.geo.Point;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "finalPlace")
public class FinalPlace {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Size(min = 5, max = 50)
    @Column(name = "placeTitle")
    private String placeTitle;

    @Column(name = "location")
    private Point location;

    @Column(name = "finalTime")
    private LocalDateTime finalTime;

    public FinalPlace() {
    }

    public FinalPlace(String placeTitle, Point location, LocalDateTime finalTime) {
        this.placeTitle = placeTitle;
        this.location = location;
        this.finalTime = finalTime;
    }

    public Long getId() {
        return id;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public LocalDateTime getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(LocalDateTime finalTime) {
        this.finalTime = finalTime;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "FinalPlace{" +
                "id=" + id +
                ", placeTitle='" + placeTitle + '\'' +
                ", location=" + location +
                ", finalTime=" + finalTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalPlace that = (FinalPlace) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(placeTitle, that.placeTitle) &&
                Objects.equals(location, that.location) &&
                Objects.equals(finalTime, that.finalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, placeTitle, location, finalTime);
    }
}
