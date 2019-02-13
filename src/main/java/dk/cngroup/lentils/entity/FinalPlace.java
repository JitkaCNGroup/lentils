package dk.cngroup.lentils.entity;

import org.springframework.data.geo.Point;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "final_place")
public class FinalPlace {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Size(min = 5, max = 50)
    @Column(name = "title")
    private String title;

    @Column(name = "location")
    private Point location;

    @Column(name = "opening_time")
    private LocalDateTime openingTime;

    public FinalPlace() {
    }

    public FinalPlace(String title, Point location, LocalDateTime openingTime) {
        this.title = title;
        this.location = location;
        this.openingTime = openingTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalDateTime openingTime) {
        this.openingTime = openingTime;
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
                ", title='" + title + '\'' +
                ", location=" + location +
                ", openingTime=" + openingTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalPlace that = (FinalPlace) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(location, that.location) &&
                Objects.equals(openingTime, that.openingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, location, openingTime);
    }
}
