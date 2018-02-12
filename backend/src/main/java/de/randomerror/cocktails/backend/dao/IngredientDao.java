package de.randomerror.cocktails.backend.dao;

import de.randomerror.cocktails.backend.App;
import de.randomerror.cocktails.backend.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public class IngredientDao {
    public static List<Ingredient> findAll() {
        return App.session.createQuery("from Ingredient", Ingredient.class).list();
    }

    public static Optional<Ingredient> findById(long id) {
        return Optional.ofNullable(App.session.find(Ingredient.class, id));
    }

    public static long save(Ingredient cocktail) {
        return (long) App.session.save(cocktail);
    }

    public static void delete(Ingredient cocktail) {
        App.session.delete(cocktail);
    }
}
