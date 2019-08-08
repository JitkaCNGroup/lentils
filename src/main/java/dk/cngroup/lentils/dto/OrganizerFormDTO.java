package dk.cngroup.lentils.dto;

import dk.cngroup.lentils.entity.Cypher;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizerFormDTO {

    private Long userId;

    @NotEmpty(message = "Jméno nesmí být prázdné")
    private String username;

    private String password;

    private List<Cypher> cyphers;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<Cypher> getCyphers() {
        return cyphers;
    }

    public void setCyphers(final List<Cypher> cyphers) {
        this.cyphers = cyphers;
    }

    public List<Long> getCypherIds() {
        if (getCyphers() != null) {
            return getCyphers().stream()
                    .map(Cypher::getCypherId)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
