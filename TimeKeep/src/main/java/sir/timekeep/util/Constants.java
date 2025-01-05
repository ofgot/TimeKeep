package sir.timekeep.util;

import sir.timekeep.model.Role;

public final class Constants {

    /**
     * Default user role.
     */
    public static final Role DEFAULT_ROLE = Role.USUAL;

    /**
     * Username login form parameter.
     */
    public static final String USERNAME_PARAM = "username";

    private Constants() {
        throw new AssertionError();
    }
}
