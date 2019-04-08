package dk.cngroup.lentils.factory;

public class LoginFactory {

    public static String getRedirectUrlAfterLoginForRole(final String role) {
        switch (role) {
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
