package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game/statistics")
public class StatisticsController {

    private static final String VIEW_ORGANIZER_STATISTICS = "statistics/statistics";

    private static final String HINT_TAKEN_COUNT_TABLE = "Seřazeno podle celkového počtu použitých nápověd";
    private static final String HINT_TAKEN_POINTS_HEADER = "Seřazeno podle celkového počtu bodů za nápovědy";
    private static final String SKIPPED_TEAMS_COUNT_HEADER = "Seřazeno podle počtu týmů, kteří přeskočili";
    private static final String SOLVED_TEAMS_COUNT_HEADER = "Seřazeno podle počtu týmů, kteří vyřešili";
    private static final String HINT_TAKEN_COUNT_COLUMN = "Počet nápověd";
    private static final String HINT_TAKEN_POINTS_COLUMN = "Body za nápovědy";
    private static final String SKIPPED_TEAMS_COLUMN = "Týmů přeskočilo";
    private static final String SOLVED_TEAMS_COLUMN = "Týmů vyřešilo";
    private static final String CYPHER_STATISTICS_HEADER = "Statistiky šifer";
    private static final String TEAM_STATISTICS_HEADER = "Statistiky týmů";
    private static final String SOLVED_CYPHERS_COLUMN = "Vyřešených šifer";
    private static final String SOLVED_CYPHERS_COUNT_HEADER = "Seřazeno podle počtu vyřešených šifer";
    private static final String SKIPPED_CYPHERS_COLUMN = "Přeskočených šifer";
    private static final String SKIPPED_CYPHERS_COUNT_HEADER = "Seřazeno podle počtu přeskočených šifer";

    private static final String TEMPLATE_ATTR_STATS_MAP = "statisticsMap";
    private static final String TEMPLATE_ATTR_TABLE_LABEL = "sortByLabel";
    private static final String TEMPLATE_ATTR_COLUMN_LABEL = "columnLabel";
    private static final String TEMPLATE_ATTR_HEADER = "header";

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public String statisticsView(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADER, CYPHER_STATISTICS_HEADER);
        return VIEW_ORGANIZER_STATISTICS;
    }

    @GetMapping("/cyphersByCountOfHintsTaken")
    public String countOfHintTakenForCyphers(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADER, CYPHER_STATISTICS_HEADER);
        model.addAttribute(TEMPLATE_ATTR_STATS_MAP, statisticsService.getNumberOfHintsTakenOfCyphers());
        model.addAttribute(TEMPLATE_ATTR_TABLE_LABEL, HINT_TAKEN_COUNT_TABLE);
        model.addAttribute(TEMPLATE_ATTR_COLUMN_LABEL, HINT_TAKEN_COUNT_COLUMN);
        return VIEW_ORGANIZER_STATISTICS;
    }

    @GetMapping("/cyphersByPointsOfHintsTaken")
    public String pointsOfHintTakenForCyphers(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADER, CYPHER_STATISTICS_HEADER);
        model.addAttribute(TEMPLATE_ATTR_STATS_MAP, statisticsService.getPointsOfHintsTakenOfCyphers());
        model.addAttribute(TEMPLATE_ATTR_TABLE_LABEL, HINT_TAKEN_POINTS_HEADER);
        model.addAttribute(TEMPLATE_ATTR_COLUMN_LABEL, HINT_TAKEN_POINTS_COLUMN);
        return VIEW_ORGANIZER_STATISTICS;
    }

    @GetMapping("/cyphersBySkippedCount")
    public String bySkippedCyphers(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADER, CYPHER_STATISTICS_HEADER);
        model.addAttribute(
                TEMPLATE_ATTR_STATS_MAP,
                statisticsService.getCountOfSpecificStatusesOfCyphers(CypherStatus.SKIPPED));
        model.addAttribute(TEMPLATE_ATTR_TABLE_LABEL, SKIPPED_TEAMS_COUNT_HEADER);
        model.addAttribute(TEMPLATE_ATTR_COLUMN_LABEL, SKIPPED_TEAMS_COLUMN);
        return VIEW_ORGANIZER_STATISTICS;
    }

    @GetMapping("/cyphersBySolvedCount")
    public String bySolvedCyphers(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADER, CYPHER_STATISTICS_HEADER);
        model.addAttribute(
                TEMPLATE_ATTR_STATS_MAP,
                statisticsService.getCountOfSpecificStatusesOfCyphers(CypherStatus.SOLVED));
        model.addAttribute(TEMPLATE_ATTR_TABLE_LABEL, SOLVED_TEAMS_COUNT_HEADER);
        model.addAttribute(TEMPLATE_ATTR_COLUMN_LABEL, SOLVED_TEAMS_COLUMN);
        return VIEW_ORGANIZER_STATISTICS;
    }

    @GetMapping("/teamsBySolvedCyphers")
    public String teamsBySolvedCyphers(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADER, TEAM_STATISTICS_HEADER);
        model.addAttribute(
                TEMPLATE_ATTR_STATS_MAP,
                statisticsService.getCountOfSpecificStatusesOfTeams(CypherStatus.SOLVED));
        model.addAttribute(TEMPLATE_ATTR_TABLE_LABEL, SOLVED_CYPHERS_COUNT_HEADER);
        model.addAttribute(TEMPLATE_ATTR_COLUMN_LABEL, SOLVED_CYPHERS_COLUMN);
        return VIEW_ORGANIZER_STATISTICS;
    }

    @GetMapping("/teamsBySkippedCyphers")
    public String teamsBySkippedCyphers(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADER, TEAM_STATISTICS_HEADER);
        model.addAttribute(
                TEMPLATE_ATTR_STATS_MAP,
                statisticsService.getCountOfSpecificStatusesOfTeams(CypherStatus.SKIPPED));
        model.addAttribute(TEMPLATE_ATTR_TABLE_LABEL, SKIPPED_CYPHERS_COUNT_HEADER);
        model.addAttribute(TEMPLATE_ATTR_COLUMN_LABEL, SKIPPED_CYPHERS_COLUMN);
        return VIEW_ORGANIZER_STATISTICS;
    }

    @GetMapping("/teamsByPointsOfHintsTaken")
    public String pointsOfHintTakenForTeams(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADER, TEAM_STATISTICS_HEADER);
        model.addAttribute(TEMPLATE_ATTR_STATS_MAP, statisticsService.getPointsOfHintsTakenOfTeams());
        model.addAttribute(TEMPLATE_ATTR_TABLE_LABEL, HINT_TAKEN_POINTS_HEADER);
        model.addAttribute(TEMPLATE_ATTR_COLUMN_LABEL, HINT_TAKEN_POINTS_COLUMN);
        return VIEW_ORGANIZER_STATISTICS;
    }

    @GetMapping("/teamsByCountOfHintsTaken")
    public String countOfHintTakenForTeams(final Model model) {
        model.addAttribute(TEMPLATE_ATTR_HEADER, TEAM_STATISTICS_HEADER);
        model.addAttribute(TEMPLATE_ATTR_STATS_MAP, statisticsService.getNumberOfHintsTakenOfTeams());
        model.addAttribute(TEMPLATE_ATTR_TABLE_LABEL, HINT_TAKEN_COUNT_TABLE);
        model.addAttribute(TEMPLATE_ATTR_COLUMN_LABEL, HINT_TAKEN_COUNT_COLUMN);
        return VIEW_ORGANIZER_STATISTICS;
    }
}
