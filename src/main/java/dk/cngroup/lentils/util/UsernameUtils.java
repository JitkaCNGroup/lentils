package dk.cngroup.lentils.util;

import java.text.Normalizer;

public class UsernameUtils {

    public static String generateUsername(final String oldUsername) {
        String newUsername = Normalizer
                .normalize(oldUsername, Normalizer.Form.NFD)
                .toLowerCase()
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("^((?:\\W*\\w+){" + 4 + "}).*$", "$1")
                .replace(" ", "_");
        return newUsername;
    }

}
