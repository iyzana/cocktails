package de.randomerror.cocktails.backend.controller;

import com.google.gson.Gson;
import de.randomerror.cocktails.backend.dao.IngredientDao;
import de.randomerror.cocktails.backend.entity.Ingredient;

import static spark.Spark.post;

public class IngredientController {
    private static final Gson gson = new Gson();

    public static void init() {
        post("/ingredient", (req, res) -> {
            Ingredient create = gson.fromJson(req.body(), Ingredient.class);

            return IngredientDao.save(create);
        });
    }
}
