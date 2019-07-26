package dk.cngroup.lentils.dto;

import org.springframework.data.geo.Point;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class FinalPlaceFormDTO {

    @Size(min = 1, max = 1000, message = "Popis musí mít 1 - 1000 znaků.")
    private String description;

    @NotNull(message = "Souřadnice nesmí být prázdné.")
    private Point location;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime resultsTime;

    @NotNull(message = "Zpřístupnění nesmí být prázdné.")
    @Min(value = 0, message = "Zpřístupnění nesmí být záporné.")
    private Integer accessTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime finishTime;

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

    public Integer getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(final Integer accessTime) {
        this.accessTime = accessTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(final LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }
}
