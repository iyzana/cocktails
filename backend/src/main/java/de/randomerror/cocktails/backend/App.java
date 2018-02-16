package de.randomerror.cocktails.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.randomerror.cocktails.backend.controller.CocktailController;
import de.randomerror.cocktails.backend.controller.IngredientController;
import de.randomerror.cocktails.backend.controller.LoginController;
import de.randomerror.cocktails.backend.dto.ErrorDto;
import de.randomerror.cocktails.backend.entity.Cocktail;
import de.randomerror.cocktails.backend.entity.Ingredient;
import de.randomerror.cocktails.backend.entity.Input;
import de.randomerror.cocktails.backend.exception.AuthenticationException;
import de.randomerror.cocktails.backend.exception.NotFoundException;
import org.aeonbits.owner.ConfigFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import static spark.Spark.*;

public class App {
    public static Session dbSession;
    public static Gson gson;
    public static AppConfig config;

    public static void main(String[] args) {
        dbSession = configureDbSession();
        gson = new GsonBuilder().setPrettyPrinting().create();
        config = ConfigFactory.create(AppConfig.class);

        after("/*", (req, res) -> {
            res.type("application/json");
        });

        errorHandlers();

        LoginController.routes();
        path("/cocktail", CocktailController::routes);
        path("/ingredient", IngredientController::routes);
    }

    private static Session configureDbSession() {
        return new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", "jdbc:h2:./cocktails")
                .setProperty("hibernate.connection.username", "")
                .setProperty("hibernate.connection.password", "")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .addAnnotatedClass(Cocktail.class)
                .addAnnotatedClass(Input.class)
                .addAnnotatedClass(Ingredient.class)
                .buildSessionFactory()
                .openSession();
    }

    private static void errorHandlers() {
        internalServerError((req, res) -> {
            res.status(500);
            res.type("application/json");
            return gson.toJson(new ErrorDto("internal server error", "unknown cause"));
        });

        notFound((req, res) -> {
            res.status(404);
            res.type("application/json");
            return gson.toJson(new ErrorDto("not found", req.requestMethod() + " " + req.pathInfo()));
        });

        exception(AuthenticationException.class, (ex, req, res) -> {
            res.status(401);
            res.type("application/json");
            res.body(gson.toJson(new ErrorDto("unauthorized", ex.getMessage())));
        });

        exception(NotFoundException.class, (ex, req, res) -> {
            res.status(404);
            res.type("application/json");
            res.body(gson.toJson(new ErrorDto("not found", ex.getMessage())));
        });

        exception(NumberFormatException.class, (ex, req, res) -> {
            res.status(400);
            res.type("application/json");
            res.body(gson.toJson(new ErrorDto("bad request", "could not parse number " + ex.getMessage())));
        });
    }
}
