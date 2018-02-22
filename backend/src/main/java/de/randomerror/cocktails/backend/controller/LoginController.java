package de.randomerror.cocktails.backend.controller;

import de.randomerror.cocktails.backend.App;
import de.randomerror.cocktails.backend.dto.LoginDto;
import de.randomerror.cocktails.backend.exception.AuthenticationException;

import static spark.Spark.before;
import static spark.Spark.post;

public class LoginController {
    private static final String ROUTE = "/login";
    private static final String SESSION_ATTRIBUTE = "user";

    public static void registerRoutes() {
        before((req, res) -> {
            // allow any login request
            if (ROUTE.equals(req.pathInfo()) && "POST".equals(req.requestMethod()))
                return;

            // deny all other unauthenticated requests
            if (req.session().attribute(SESSION_ATTRIBUTE) == null)
                throw new AuthenticationException("not logged in");
        });

        post(ROUTE, (req, res) -> {
            LoginDto login = App.gson.fromJson(req.body(), LoginDto.class);

            if (!App.config.password().equals(login.getPassword()))
                throw new AuthenticationException("wrong password");

            req.session().attribute(SESSION_ATTRIBUTE, login.getName());

            res.status(200);
            return "ok";
        }, App.gson::toJson);
    }
}
