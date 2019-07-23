package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.dto.CypherFormDTO;
import dk.cngroup.lentils.entity.Cypher;

public interface ObjectMapper {
    void map(Object source, Object destination);
    <T> T map(Object source, Class<T> destinationClassName);
    Cypher map(CypherFormDTO source);
    CypherFormDTO map(Cypher source);
    void map(CypherFormDTO source, Cypher destination);
}