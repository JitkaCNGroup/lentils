package dk.cngroup.lentils.util;

import java.text.Normalizer;

public final class CzechCharsetUtils {

    private CzechCharsetUtils() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static String replaceCzechSpecialCharacters(final String oldUsername) {
        return Normalizer
                .normalize(oldUsername, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
