package dk.cngroup.lentils.controller;

import com.itextpdf.text.DocumentException;
import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.view.TeamScoreDetail;
import dk.cngroup.lentils.service.PdfExportService;
import dk.cngroup.lentils.service.ScoreService;
import dk.cngroup.lentils.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping("/game/score")
public class ScoreController {
    private static final String SCORE_LIST = "score/list";
    private static final String SCORE_LIST_DETAIL = "score/team";
    private static final String ERROR = "error/error";

    private final TeamService teamService;
    private final ScoreService scoreService;
    private final PdfExportService exportService;

    @Autowired
    public ScoreController(final TeamService teamService,
                           final ScoreService scoreService,
                           final PdfExportService exportService) {
        this.teamService = teamService;
        this.scoreService = scoreService;
        this.exportService = exportService;
    }

    @GetMapping
    public String listScore(final Model model) {
        model.addAttribute("teamsWithScores", scoreService.getAllTeamsWithScores());
        return SCORE_LIST;
    }

    @GetMapping(value = "/team")
    public String viewDetailScoreForTeam(final @RequestParam("teamId") Long teamId,
                                         final Model model) {
        Team team = teamService.getTeam(teamId);
        List<TeamScoreDetail> teamScoreDetails = scoreService.getTeamWithDetailScores(team);
        model.addAttribute("team", team);
        model.addAttribute("teamWithDetailScores", teamScoreDetails);
        return SCORE_LIST_DETAIL;
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> exportScores() throws DocumentException {
        ByteArrayInputStream bis = exportService.exportScoresToPdf();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
