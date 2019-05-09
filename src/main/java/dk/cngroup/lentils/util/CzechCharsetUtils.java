package dk.cngroup.lentils.util;

import java.text.Normalizer;

public class CzechCharsetUtils {
    public static String replaceCzechSpecialCharacters(String oldUsername) {
        return Normalizer
                .normalize(oldUsername, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
