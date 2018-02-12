package de.randomerror.cocktails.backend.dao;

import de.randomerror.cocktails.backend.entity.Ingredient;

import java.util.List;
import java.util.Optional;

import static de.randomerror.cocktails.backend.dao.Hibernate.dbTransaction;

public class IngredientDao {
    public static List<Ingredient> findAll() {
        return dbTransaction(session -> {
            return session.createQuery("select c from Ingredient c", Ingredient.class).list();
        });
    }

    public static Optional<Ingredient> findById(long id) {
        return dbTransaction(session -> {
            return Optional.ofNullable(session.find(Ingredient.class, id));
        });
    }

    public static Ingredient save(Ingredient ingredient) {
        return dbTransaction(session -> {
            session.save(ingredient);
            return ingredient;
        });
    }

    public static void delete(Ingredient ingredient) {
        dbTransaction(session -> {
            session.delete(ingredient);
            return "ok";
        });
    }
}

