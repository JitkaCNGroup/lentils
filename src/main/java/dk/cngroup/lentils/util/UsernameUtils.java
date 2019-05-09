package dk.cngroup.lentils.util;

import static dk.cngroup.lentils.util.CzechCharsetUtils.replaceCzechSpecialCharacters;

public class UsernameUtils {

    public static String generateUsername(final String oldUsername) {
        final int allowedWords = 4;
        String newUsername = replaceCzechSpecialCharacters(oldUsername)
                .toLowerCase()
                .replaceAll("^((?:\\W*\\w+){" + allowedWords + "}).*$", "$1")
                .replace(" ", "_");
        return newUsername;
    }
}