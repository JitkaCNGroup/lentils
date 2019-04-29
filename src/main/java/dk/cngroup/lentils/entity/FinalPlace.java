package dk.cngroup.lentils.entity;

import org.springframework.data.geo.Point;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "final_place")
public class FinalPlace {

    @Id
    @GeneratedValue
    @Column(name = "final_place_id")
    private Long finalPlaceId;

    @Column(name = "description")
    @Size(min = 1, max = 1000, message = "Popis musí mít 1 - 1000 znaků.")
    private String description;

    @Column(name = "location")
    @NotNull(message = "Souřadnice nesmí být prázdné.")
    private Point location;

    @Column(name = "opening_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime openingTime;

    public FinalPlace() {
    }

    public FinalPlace(final String description, final Point location, final LocalDateTime openingTime) {
        this.description = description;
        this.location = location;
        this.openingTime = openingTime;
    }

    public LocalTime getOpeningTimeOnly() {
        return openingTime.toLocalTime();
    }

    public Long getFinalPlaceId() {
        return finalPlaceId;
    }

    public void setFinalPlaceId(final Long finalPlaceId) {
        this.finalPlaceId = finalPlaceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(final LocalDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(final Point location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "FinalPlace{" +
                "finalPlaceId=" + finalPlaceId +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", openingTime=" + openingTime +
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
        FinalPlace that = (FinalPlace) o;
        return Objects.equals(finalPlaceId, that.finalPlaceId) &&
                Objects.equals(description, that.description) &&
                Objects.equals(location, that.location) &&
                Objects.equals(openingTime, that.openingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(finalPlaceId, description, location, openingTime);
    }
}
