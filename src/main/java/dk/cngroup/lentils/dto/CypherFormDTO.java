package dk.cngroup.lentils.dto;

import org.springframework.data.geo.Point;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CypherFormDTO {
    @NotEmpty(message = "Jméno nesmí být prázdné.")
    private String name;

    @NotNull(message = "Pořadí nesmí být prázdné.")
    @Min(value = 1, message = "Pořadí musí být větší než 0.")
    private int stage;

    @NotNull(message = "Souřadnice nesmí být prázdné.")
    private Point location;

    @NotEmpty(message = "Adresa mapy nesmí být prázdná.")
    private String mapAddress;

    private String codeword;

    private String trapCodeword;

    @Size(max = 4000, message = "Bonusové informace nesmí být delší než 4000 znaků.")
    private String bonusContent;

    @Size(max = 2000, message = "Popis místa nesmí být delší než 2000 znaků.")
    private String placeDescription;

    private List<Long> organizers;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(final int stage) {
        this.stage = stage;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(final Point location) {
        this.location = location;
    }

    public String getMapAddress() {
        return mapAddress;
    }

    public void setMapAddress(final String mapAddress) {
        this.mapAddress = mapAddress;
    }

    public String getCodeword() {
        return codeword;
    }

    public void setCodeword(final String codeword) {
        this.codeword = codeword;
    }

    public String getTrapCodeword() {
        return trapCodeword;
    }

    public void setTrapCodeword(final String trapCodeword) {
        this.trapCodeword = trapCodeword;
    }

    public String getBonusContent() {
        return bonusContent;
    }

    public void setBonusContent(final String bonusContent) {
        this.bonusContent = bonusContent;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(final String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public List<Long> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(final List<Long> organizers) {
        this.organizers = organizers;
    }
}
