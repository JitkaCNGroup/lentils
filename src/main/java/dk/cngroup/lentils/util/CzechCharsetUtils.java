package dk.cngroup.lentils.util;

import java.text.Normalizer;

public class CzechCharsetUtils {
    public static String replaceCzechSpecialCharacters(final String oldUsername) {
        return Normalizer
                .normalize(oldUsername, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
