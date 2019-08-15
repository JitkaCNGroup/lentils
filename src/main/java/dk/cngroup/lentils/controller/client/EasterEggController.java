package dk.cngroup.lentils.controller.client;

import dk.cngroup.lentils.security.CustomUserDetails;
import dk.cngroup.lentils.service.CypherService;
import dk.cngroup.lentils.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class EasterEggController {

    private static final String VIEW_CLIENT_CYPHER_TRAP = "client/cypher/trap";

    private static final String TEMPLATE_ATTR_CYPHER = "cypher";
    private static final String TEMPLATE_ATTR_SCORE = "score";
    private static final String TEMPLATE_ATTR_TEAM = "team";

    private final CypherService cypherService;
    private final ScoreService scoreService;

    @Autowired
    public EasterEggController(final CypherService cypherService,
                            final ScoreService scoreService) {
        this.cypherService = cypherService;
        this.scoreService = scoreService;
    }

    @GetMapping("cypher/trap/{id}")
    public String trapScreen(@PathVariable("id") final Long id,
                             @AuthenticationPrincipal final CustomUserDetails user,
                             final Model model) {
        model.addAttribute(TEMPLATE_ATTR_TEAM, user.getTeam());
        model.addAttribute(TEMPLATE_ATTR_SCORE, scoreService.getScoreByTeam(user.getTeam()));
        model.addAttribute(TEMPLATE_ATTR_CYPHER, cypherService.getCypher(id));
        return VIEW_CLIENT_CYPHER_TRAP;
    }
}
