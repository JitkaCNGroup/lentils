package dk.cngroup.lentils.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class AssertionUtils {
    private final static String REDIRECTION_PREFIX = "redirect:/";

    private AssertionUtils() {
    }

    public static void assertValueIsNotRedirection(final String target) {
        assertFalse(target.startsWith(REDIRECTION_PREFIX));
    }

    public static void assertValueIsRedirection(final String target) {
        assertTrue(target.startsWith(REDIRECTION_PREFIX));
    }
}
