package de.randomerror.cocktails.backend.controller;


import de.randomerror.cocktails.backend.App;
import de.randomerror.cocktails.backend.dao.CocktailDao;
import de.randomerror.cocktails.backend.dao.IngredientDao;
import de.randomerror.cocktails.backend.dto.CreateCocktailDto;
import de.randomerror.cocktails.backend.entity.Cocktail;
import de.randomerror.cocktails.backend.entity.Ingredient;
import de.randomerror.cocktails.backend.entity.Input;
import de.randomerror.cocktails.backend.exception.InvalidInputException;
import de.randomerror.cocktails.backend.exception.NotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class CocktailController {
    private static final double MAX_UNITS = 9 + Math.PI * 0.1;

    public static void routes() {
        get("", (req, res) -> CocktailDao.findAll(), App.gson::toJson);

        post("", (req, res) -> {
            CreateCocktailDto create = App.gson.fromJson(req.body(), CreateCocktailDto.class);
            Cocktail c = new Cocktail(create.getName(), buildInputs(create.getInputs()));
            return CocktailDao.save(c);
        }, App.gson::toJson);

        get("/:id", (req, res) -> {
            int id = parseInt(req.params("id"));
            return CocktailDao.findById(id)
                    .orElseThrow(() -> new NotFoundException("cocktail", id));
        }, App.gson::toJson);

        delete("/:id", (req, res) -> {
            int id = parseInt(req.params("id"));
            Cocktail cocktail = CocktailDao.findById(id)
                    .orElseThrow(() -> new NotFoundException("cocktail", id));
            CocktailDao.delete(cocktail);

            res.status(204);
            return null;
        }, App.gson::toJson);
    }

    private static List<Input> buildInputs(Map<Long, Double> createInputs) {
        List<Input> inputs = new LinkedList<>();
        double size = 0;

        for (Map.Entry<Long, Double> entry : createInputs.entrySet()) {
            Ingredient ingredient = IngredientDao.findById(entry.getKey())
                    .orElseThrow(() -> new NotFoundException("ingredient", entry.getKey()));

            double amount = entry.getValue();
            if (amount <= 0)
                throw new InvalidInputException("empty amount");
            size += amount;

            inputs.add(new Input(ingredient, amount));
        }

        if (size > MAX_UNITS)
            throw new InvalidInputException("too many inputs");

        return inputs;
    }
}
