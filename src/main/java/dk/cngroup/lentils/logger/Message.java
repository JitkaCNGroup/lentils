package dk.cngroup.lentils.logger;

import dk.cngroup.lentils.entity.Team;

import java.time.LocalDateTime;
import java.util.Optional;

public class Message<T> {
    private static final String MESSAGE_FORMAT = "[%s] '%s' by %s%s%s %s, points: %d, score: %d";
    private static final String FOR_TEAM_MESSAGE_FORMAT = " for team %d %s,";
    private static final String USER_MESSAGE_FORMAT = " %d %s,";

    private final LocalDateTime timestamp;
    private final Action action;
    private final Author author;
    private final Team team;
    private final Team forTeam;
    private final T data;
    private final int points;
    private final int score;

    public Message(final Action action,
                   final Author author,
                   final Team team,
                   final Team forTeam,
                   final T data,
                   final int points,
                   final int score) {
        this.timestamp = LocalDateTime.now();
        this.author = author;
        this.action = action;
        this.team = team;
        this.forTeam = forTeam;
        this.data = data;
        this.points = points;
        this.score = score;
    }

    @Override
    public String toString() {
        String forTeamMessagePart = getForTeamPart();
        String teamPart = getTeamPart();
        return String.format(MESSAGE_FORMAT, timestamp, action, author, teamPart,
                forTeamMessagePart, data.toString(), points, score);
    }

    private String getTeamPart() {
        return Optional.ofNullable(team)
                .map(t -> String.format(USER_MESSAGE_FORMAT, t.getTeamId(), t.getName()))
                .orElse("");
    }

    private String getForTeamPart() {
        return Optional.ofNullable(forTeam)
                .map(t -> String.format(FOR_TEAM_MESSAGE_FORMAT, t.getTeamId(), t.getName()))
                .orElse("");
    }
}
