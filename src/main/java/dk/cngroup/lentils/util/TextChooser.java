package dk.cngroup.lentils.util;

import java.io.File;
import java.io.FilenameFilter;

public class TextChooser implements FilenameFilter {
    @Override
    public boolean accept(final File dir, final String name) {
        if (name.endsWith("*.jpg")) {
            return true;
        } else if (name.endsWith("*.png")) {
            return true;
        } else if (name.endsWith("*.gif")) {
            return true;
        }
        return false;
    }
}
