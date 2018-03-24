package de.randomerror.cocktails.backend.service;

import de.randomerror.cocktails.backend.App;
import de.randomerror.cocktails.backend.exception.AuthenticationException;
import spark.Request;

public class LoginService {
    private static final String NAME_ATTRIBUTE = "name";
    private static final String ADMIN_ATTRIBUTE = "admin";

    public static String getName(Request req) {
        return req.session().attribute(NAME_ATTRIBUTE);
    }

    public static String getUniqueName(Request req) {
        return req.session().id() + "#" + getName(req);
    }

    public static void checkLogin(String name, String password, Request req) {
        boolean passwordMatches = App.config.password().equals(password);
        boolean adminPasswordMatches = App.config.adminPassword().equals(password);
        if (!passwordMatches && !adminPasswordMatches)
            throw new AuthenticationException("wrong password");

        req.session().attribute(NAME_ATTRIBUTE, name);
        req.session().attribute(ADMIN_ATTRIBUTE, adminPasswordMatches);
    }

    public static boolean isLoggedIn(Request req) {
        return req.session().attribute(NAME_ATTRIBUTE) != null;
    }

    public static boolean isAdmin(Request req) {
        return isLoggedIn(req) && (boolean) req.session().attribute(ADMIN_ATTRIBUTE);
    }

    public static void requireAdmin(Request req) {
        if (!isAdmin(req))
            throw new AuthenticationException("not an admin");
    }
}
