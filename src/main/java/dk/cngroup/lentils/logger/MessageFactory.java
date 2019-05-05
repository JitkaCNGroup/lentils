package dk.cngroup.lentils.logger;

import dk.cngroup.lentils.entity.Team;
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
        return new Message<>(VERIFY_CODEWORD, TEAM, user.getTeam().getUser(), null, codeword, points, score);
    }

    public static Message<Long> createGetHint(final CustomUserDetails user,
                                              final Long hintId,
                                              final int points,
                                              final int score) {
        return new Message<>(GET_HINT, TEAM, user.getTeam().getUser(), null, hintId, points, score);
    }

    public static Message<Long> createSkipCypher(final CustomUserDetails user,
                                                 final Long cypherId,
                                                 final int points,
                                                 final int score) {
        return new Message<>(SKIP_CYPHER, TEAM, user.getTeam().getUser(), null, cypherId, points, score);
    }

    public static Message<StatusChange> createChangeCypherStatus(final Team team,
                                                                 final StatusChange cypherId,
                                                                 final int points,
                                                                 final int score) {
        return new Message<>(CHANGE_CYPHER_STATUS, ORGANIZER, null, team, cypherId, points, score);
    }
}
