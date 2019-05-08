package dk.cngroup.lentils.logger;

import dk.cngroup.lentils.entity.Team;
import dk.cngroup.lentils.entity.User;
import dk.cngroup.lentils.entity.formEntity.Codeword;
import dk.cngroup.lentils.security.CustomUserDetails;

import static dk.cngroup.lentils.logger.Action.*;
import static dk.cngroup.lentils.logger.Author.ORGANIZER;
import static dk.cngroup.lentils.logger.Author.TEAM;

public class MessageFactory {
    public static Message<Codeword> createVerifyCodeword(final CustomUserDetails user,
                                                         final Codeword codeword,
                                                         final int points,
                                                         final int score) {
        return createTeamMessage(VERIFY_CODEWORD, user.getTeam().getUser(), codeword, points, score);
    }

    public static Message<Long> createTakeHint(final CustomUserDetails user,
                                               final Long hintId,
                                               final int points,
                                               final int score) {
        return createTeamMessage(TAKE_HINT, user.getTeam().getUser(), hintId, points, score);
    }

    public static Message<Long> createSkipCypher(final CustomUserDetails user,
                                                 final Long cypherId,
                                                 final int points,
                                                 final int score) {
        return createTeamMessage(SKIP_CYPHER, user.getTeam().getUser(), cypherId, points, score);
    }

    public static Message<StatusChange> createChangeCypherStatus(final Team team,
                                                                 final StatusChange statusChange,
                                                                 final int points,
                                                                 final int score) {
        return createOrganizerMessage(CHANGE_CYPHER_STATUS, team, statusChange, points, score);
    }

    public static <T> Message<T> createTakeHint(final Team team,
                                                         final T hintChange,
                                                         final int points,
                                                         final int score) {
        return createOrganizerMessage(TAKE_HINT, team, hintChange, points, score);
    }

    public static <T> Message<T> createRevertHint(final Team team,
                                                final T hintChange,
                                                final int points,
                                                final int score) {
        return createOrganizerMessage(REVERT_HINT, team, hintChange, points, score);
    }

    private static <T> Message<T> createOrganizerMessage(final Action action,
                                                         final Team team,
                                                         final T data,
                                                         final int points,
                                                         final int score) {
        return new Message<>(action, ORGANIZER, null, team, data, points, score);
    }

    private static <T> Message<T> createTeamMessage(final Action action,
                                                    final User user,
                                                    final T data,
                                                    final int points,
                                                    final int score) {
        return new Message<>(action, TEAM, user, null, data, points, score);
    }
}
