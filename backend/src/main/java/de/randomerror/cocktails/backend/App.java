package de.randomerror.cocktails.backend;

import de.randomerror.cocktails.backend.controller.CocktailController;
import de.randomerror.cocktails.backend.controller.IngredientController;
import de.randomerror.cocktails.backend.entity.Cocktail;
import de.randomerror.cocktails.backend.entity.Ingredient;
import de.randomerror.cocktails.backend.entity.Input;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static spark.Spark.*;

public class App {
    public static Session session;

    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", "jdbc:h2:./cocktails")
                .setProperty("hibernate.connection.username", "")
                .setProperty("hibernate.connection.password", "")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .addAnnotatedClass(Cocktail.class)
                .addAnnotatedClass(Input.class)
                .addAnnotatedClass(Ingredient.class)
                .buildSessionFactory();

        session = sessionFactory.openSession();

        after("*", (req, res) -> {
            res.type("application/json");
        });

        internalServerError((req, res) -> {
            res.status(500);
            res.type("application/json");
            return "{\n    \"error\": \"internal server error\",\n    \"message\": \"unknown cause\"\n}";
        });

        notFound((req, res) -> {
            res.status(404);
            res.type("application/json");
            return "{\n    \"error\": \"not found\",\n    \"message\": \"" + req.requestMethod() + " " + req.pathInfo() + " could not be found\"\n}";
        });

        path("/cocktail", CocktailController::routes);
        path("/ingredient", IngredientController::routes);
    }
}
