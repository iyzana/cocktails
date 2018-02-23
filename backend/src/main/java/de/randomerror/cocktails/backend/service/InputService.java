package de.randomerror.cocktails.backend.service;

import de.randomerror.cocktails.backend.dao.IngredientDao;
import de.randomerror.cocktails.backend.entity.Ingredient;
import de.randomerror.cocktails.backend.entity.Input;
import de.randomerror.cocktails.backend.exception.InvalidInputException;
import de.randomerror.cocktails.backend.exception.NotFoundException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InputService {
    private static final double MAX_UNITS = 9 + Math.PI * 0.1;

    public static List<Input> buildInputs(Map<Long, Double> createInputs) {
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
