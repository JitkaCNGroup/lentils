package dk.cngroup.lentils.logger.utils;

import dk.cngroup.lentils.entity.CypherStatus;
import dk.cngroup.lentils.entity.Hint;

import static dk.cngroup.lentils.entity.CypherStatus.SOLVED;

public class LoggerUtils {
    public static int getTakeHintPoints(Hint hint, boolean success) {
        return getHintPoints(hint, success, -1);
    }

    public static int getRevertHintPoints(Hint hint, boolean isHintTaken) {
        return getHintPoints(hint, isHintTaken, 1);
    }

    private static int getHintPoints(Hint hint, boolean success, int sign) {
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

    public static int getVerifyCodewordPoints(String result) {
        if (result.startsWith("redirect:/cypher/")) {
            return SOLVED.getStatusValue();
        }
        return 0;
    }
}
