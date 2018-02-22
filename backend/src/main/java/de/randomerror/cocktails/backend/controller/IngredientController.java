package de.randomerror.cocktails.backend.controller;

import de.randomerror.cocktails.backend.App;
import de.randomerror.cocktails.backend.dao.IngredientDao;
import de.randomerror.cocktails.backend.entity.Ingredient;

import static spark.Spark.get;
import static spark.Spark.post;

public class IngredientController {
    private static final String ROUTE = "/ingredient";

    public static void registerRoutes() {
        get(ROUTE, (req, res) -> IngredientDao.findAll(), App.gson::toJson);
        post(ROUTE, (req, res) -> {
            Ingredient create = App.gson.fromJson(req.body(), Ingredient.class);

            return IngredientDao.save(create);
        }, App.gson::toJson);
    }
}
