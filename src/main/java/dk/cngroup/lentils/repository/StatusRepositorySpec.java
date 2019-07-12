package dk.cngroup.lentils.repository;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Status;
import org.springframework.data.jpa.domain.Specification;

public final class StatusRepositorySpec {

    private StatusRepositorySpec() {
        throw new IllegalStateException("Static factory cannot be instantiated");
    }

    public static Specification<Status> hasCypher(final Cypher cypher) {
        if (cypher != null) {
            return (status, cQuery, cBuilder) ->
                    cBuilder.equal(status.get("cypher").get("cypherId"), cypher.getCypherId());
        }
        return Specification.where(null);
    }

    public static Specification<Status> hasCypherStatus(final CypherStatus cypherStatus) {
        if (cypherStatus != null) {
            return (status, cQuery, cBuilder) ->
                    cBuilder.equal(status.get("cypherStatus"), cypherStatus);
        }
        return Specification.where(null);
    }

    public static Specification<Status> hasNotCypherStatus(final CypherStatus cypherStatus) {
        if (cypherStatus != null) {
            return (status, cQuery, cBuilder) ->
                    cBuilder.notEqual(status.get("cypherStatus"), cypherStatus);
        }
        return Specification.where(null);
    }

    public static Specification<Status> hasTeamName(final String teamName) {
        if (teamName != null) {
            String searchName = "%".concat(teamName).concat("%").toLowerCase();
            return (status, cQuery, cBuilder) ->
                    cBuilder.like(cBuilder.lower(status.get("team").get("name")), searchName);
        }
        return Specification.where(null);
    }
}
