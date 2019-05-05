package dk.cngroup.lentils.logger;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class Message<T> {
    private static final String MESSAGE_FORMAT = "[%s] '%s' by %s %d %s%s, %s, points: %d, score: %d";
    private static final String FOR_TEAM_MESSAGE_FORMAT = " (?for team %d %s)";

    private final LocalDateTime timestamp;
    private final Action action;
    private final Author author;
    private final User user;
    private final Team forTeam;
    private final T data;
    private final int points;
    private final int score;

    public Message(Action action, Author author, User user, Team forTeam, T data, int points, int score) {
        this.timestamp = LocalDateTime.now();
        this.author = author;
        this.action = action;
        this.user = user;
        this.forTeam = forTeam;
        this.data = data;
        this.points = points;
        this.score = score;
    }

    @Override
    public String toString() {
        String forTeamMessagePart = getForTeamMessagePart();
        return String.format(MESSAGE_FORMAT, timestamp, action, author, user.getUserId(), user.getUsername(),
                forTeamMessagePart, data.toString(), points, score);
    }

    private String getForTeamMessagePart() {
        return Optional.ofNullable(forTeam)
                .map(t -> String.format(FOR_TEAM_MESSAGE_FORMAT, t.getTeamId(), t.getName()))
                .orElse("");
    }
    // [TIME] 'ACTION' by (team|organizer) ID NAME (?for team ID NAME), ACTION-DATA, body: CELKOVY POCET BODU
}
