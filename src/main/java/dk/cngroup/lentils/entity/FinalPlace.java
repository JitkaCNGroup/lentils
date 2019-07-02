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

    @Column(name = "results_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime resultsTime;

    @Column(name = "access_time")
//    @NotNull(message = "Zpřístupnění nesmi být prázdné.")
//    @Min(value = 0, message = "Zpřístupnění nesmí být záporné.")
//    po odkomentovani odstranit hodnotu 1
    private Integer accessTime = 1;

    @Column(name = "finish_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime finishTime;

    public FinalPlace() {
    }

    public FinalPlace(final String description,
                      final Point location,
                      final LocalDateTime finishTime,
                      final LocalDateTime resultsTime,
                      final Integer accessTime) {
        this.description = description;
        this.location = location;
        this.finishTime = finishTime;
        this.resultsTime = resultsTime;
        this.accessTime = accessTime;
    }

    public LocalTime getResultsTimeOnly() {
        return resultsTime.toLocalTime();
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

    public Point getLocation() {
        return location;
    }

    public void setLocation(final Point location) {
        this.location = location;
    }

    public LocalDateTime getResultsTime() {
        return resultsTime;
    }

    public void setResultsTime(final LocalDateTime resultsTime) {
        this.resultsTime = resultsTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(final LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(final Integer accessTime) {
        this.accessTime = accessTime;
    }

    @Override
    public String toString() {
        return "FinalPlace{" +
                "finalPlaceId=" + finalPlaceId +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", resultsTime=" + resultsTime +
                ", finishTime=" + finishTime +
                ", accessTime=" + accessTime +
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
                Objects.equals(resultsTime, that.resultsTime) &&
                Objects.equals(finishTime, that.finishTime) &&
                Objects.equals(accessTime, that.accessTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(finalPlaceId, description, location, resultsTime, finishTime, accessTime);
    }
}
