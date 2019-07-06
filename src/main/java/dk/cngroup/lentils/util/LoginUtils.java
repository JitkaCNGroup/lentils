package dk.cngroup.lentils.util;

import java.util.Collection;

public final class LoginUtils {

    private LoginUtils() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static String getRedirectUrlAfterLoginForRole(final Collection role) {
        switch (role.toString()) {
            case "[ROLE_ADMIN]":
                return "redirect:/admin/team";
            case "[ROLE_ORGANIZER]":
                return "redirect:/game/progress";
            case "[ROLE_USER]":
                return "redirect:/cypher";
            default:
                return "redirect:/login";
        }
    }
}
