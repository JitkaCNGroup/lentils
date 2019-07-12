package dk.cngroup.lentils.logger.utils;

import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;

import static dk.cngroup.lentils.entity.CypherStatus.SOLVED;

public final class LoggerUtils {

    private LoggerUtils() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static int getTakeHintPoints(final Hint hint, final boolean success) {
        return getHintPoints(hint, success, -1);
    }

    public static int getRevertHintPoints(final Hint hint, final boolean isHintTaken) {
        return getHintPoints(hint, isHintTaken, 1);
    }

    private static int getHintPoints(final Hint hint, final boolean success, final int sign) {
        return success ? sign * hint.getValue() : 0;
    }

    public static int getChangeCypherStatusPoints(final CypherStatus oldCypherStatus,
                                                  final CypherStatus newCypherStatus) {
        if (newCypherStatus == SOLVED && oldCypherStatus != SOLVED) {
            return SOLVED.getStatusValue();
        } else if (newCypherStatus != SOLVED && oldCypherStatus == SOLVED) {
            return -SOLVED.getStatusValue();
        } else {
            return 0;
        }
    }

    public static int getVerifyCodewordPoints(final String result) {
        if (result.startsWith("redirect:/cypher/")) {
            return SOLVED.getStatusValue();
        }
        return 0;
    }
}
