package dk.cngroup.lentils.entity.formEntity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TeamDTO {
    @NotEmpty(message = "Jméno nesmí být prázdné.")
    @Length(max = 50, message = "Jméno nesmí být delší než 50 znaků.")
    private String name;

    @NotNull(message = "Počet členů nesmí být prázdný.")
    @Min(value = 1, message = "Počet členů musí být vetší než 0.")
    private Integer numOfMembers;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getNumOfMembers() {
        return numOfMembers;
    }

    public void setNumOfMembers(final Integer numOfMembers) {
        this.numOfMembers = numOfMembers;
    }
}
