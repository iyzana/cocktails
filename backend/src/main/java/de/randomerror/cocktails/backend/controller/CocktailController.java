package de.randomerror.cocktails.backend.controller;


import com.google.gson.Gson;
import de.randomerror.cocktails.backend.dao.CocktailDao;
import de.randomerror.cocktails.backend.dao.IngredientDao;
import de.randomerror.cocktails.backend.dto.CreateCocktailDto;
import de.randomerror.cocktails.backend.entity.Cocktail;
import de.randomerror.cocktails.backend.entity.Ingredient;
import de.randomerror.cocktails.backend.entity.Input;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class CocktailController {
    private static final Gson gson = new Gson();
    private static final double MAX_UNITS = 9 + Math.PI * 0.1;

    public static void init() {
        get("/cocktail", (req, res) -> CocktailDao.findAll(), gson::toJson);

        post("/cocktail", (req, res) -> {
            CreateCocktailDto create = gson.fromJson(req.body(), CreateCocktailDto.class);
            Cocktail c = new Cocktail(create.getName(), buildInputs(create.getInputs()));
            return CocktailDao.save(c);
        }, gson::toJson);
    }

    private static List<Input> buildInputs(Map<Long, Double> createInputs) {
        List<Input> inputs = new LinkedList<>();
        double size = 0;

        for (Map.Entry<Long, Double> entry : createInputs.entrySet()) {
            Ingredient ingredient = IngredientDao.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("ingredient does not exist"));

            double amount = entry.getValue();
            if (amount <= 0)
                throw new RuntimeException("empty amount");
            size += amount;

            inputs.add(new Input(ingredient, amount));
        }

        if (size > MAX_UNITS)
            throw new RuntimeException("too many inputs");

        return inputs;
    }
}
