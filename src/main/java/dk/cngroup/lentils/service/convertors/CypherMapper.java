package dk.cngroup.lentils.service.convertors;

import dk.cngroup.lentils.dto.CypherFormDTO;
import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.service.UserService;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CypherMapper extends ModelMapperWrapper {

    private UserService userService;

    private final Converter<List<Long>, List<User>> idToOrganizersConverter =
            ctx -> ctx.getSource() == null ? null : userService.getOrganizersByIds(ctx.getSource());

    private final Converter<List<User>, List<Long>> organizersToIdConverter =
            ctx -> ctx.getSource() == null ? null : ctx.getSource().stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());

    @Autowired
    public CypherMapper(final UserService userService) {
        this.userService = userService;

        super.getModelMapper().typeMap(CypherFormDTO.class, Cypher.class)
                .addMappings(mapper -> mapper.using(idToOrganizersConverter)
                        .map(CypherFormDTO::getOrganizers, Cypher::setOrganizers));

        super.getModelMapper().typeMap(Cypher.class, CypherFormDTO.class)
                .addMappings(mapper -> mapper.using(organizersToIdConverter)
                        .map(Cypher::getOrganizers, CypherFormDTO::setOrganizers));
    }
}
