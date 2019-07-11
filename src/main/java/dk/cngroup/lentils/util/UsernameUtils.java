package dk.cngroup.lentils.util;

import static dk.cngroup.lentils.util.CzechCharsetUtils.replaceCzechSpecialCharacters;

public final class UsernameUtils {

    private UsernameUtils() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static String generateUsername(final String oldUsername) {
        final int allowedWords = 4;

        return replaceCzechSpecialCharacters(oldUsername)
                .toLowerCase()
                .replaceAll("^((?:\\W*\\w+){" + allowedWords + "}).*$", "$1")
                .replace(" ", "_");
    }
}