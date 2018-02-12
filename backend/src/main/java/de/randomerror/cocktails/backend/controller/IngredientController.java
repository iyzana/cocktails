package de.randomerror.cocktails.backend.controller;

import com.google.gson.Gson;
import de.randomerror.cocktails.backend.dao.IngredientDao;
import de.randomerror.cocktails.backend.entity.Ingredient;

import static spark.Spark.get;
import static spark.Spark.post;

public class IngredientController {
    private static final Gson gson = new Gson();

    public static void routes() {
        get("", (req, res) -> IngredientDao.findAll(), gson::toJson);
        post("", (req, res) -> {
            Ingredient create = gson.fromJson(req.body(), Ingredient.class);

            return IngredientDao.save(create);
        }, gson::toJson);
    }
}
