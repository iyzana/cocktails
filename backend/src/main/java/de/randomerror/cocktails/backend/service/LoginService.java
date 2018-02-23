package de.randomerror.cocktails.backend.service;

import spark.Request;

public class LoginService {
    private static final String NAME_ATTRIBUTE = "name";

    public static String getName(Request req) {
        return req.session().attribute(NAME_ATTRIBUTE);
    }

    public static String getUniqueName(Request req) {
        return req.session().id() + "#" + getName(req);
    }

    public static void login(String name, Request req) {
        req.session().attribute(NAME_ATTRIBUTE, name);
    }

    public static boolean isLoggedIn(Request request) {
        return request.session().attribute(NAME_ATTRIBUTE) != null;
    }
}
