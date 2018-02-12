package de.randomerror.cocktails.backend.dao;

import de.randomerror.cocktails.backend.App;
import de.randomerror.cocktails.backend.entity.Cocktail;

import java.util.List;
import java.util.Optional;

public class CocktailDao {
    public static List<Cocktail> findAll() {
        return App.session.createQuery("select c from Cocktail c", Cocktail.class).list();
    }

    static Optional<Cocktail> findById(long id) {
        return Optional.ofNullable(App.session.find(Cocktail.class, id));
    }

    public static Cocktail save(Cocktail cocktail) {
        App.session.save(cocktail);
        return cocktail;
    }

    static void delete(Cocktail cocktail) {
        App.session.delete(cocktail);
    }
}
