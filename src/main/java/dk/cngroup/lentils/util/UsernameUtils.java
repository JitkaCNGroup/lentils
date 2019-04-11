package dk.cngroup.lentils.util;

import java.text.Normalizer;

public class UsernameUtils {

    public static String generateUsername(final String oldUsername) {
        final int allowedWords = 4;
        String newUsername = Normalizer
                .normalize(oldUsername, Normalizer.Form.NFD)
                .toLowerCase()
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("^((?:\\W*\\w+){" + allowedWords + "}).*$", "$1")
                .replace(" ", "_");
        return newUsername;
    }
}