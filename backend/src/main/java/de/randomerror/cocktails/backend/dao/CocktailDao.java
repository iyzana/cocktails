package de.randomerror.cocktails.backend.dao;

import de.randomerror.cocktails.backend.entity.Cocktail;

import java.util.List;
import java.util.Optional;

import static de.randomerror.cocktails.backend.dao.Hibernate.dbTransaction;
import static java.util.Optional.ofNullable;

public class CocktailDao {
    public static List<Cocktail> findAll() {
        return dbTransaction(session ->
                session.createQuery("select c from Cocktail c", Cocktail.class).list()
        );
    }

    public static Optional<Cocktail> findById(long id) {
        return dbTransaction(session ->
            ofNullable(session.find(Cocktail.class, id))
        );
    }

    public static Cocktail save(Cocktail cocktail) {
        return dbTransaction(session -> {
            session.save(cocktail);
            return cocktail;
        });
    }

    public static void delete(Cocktail cocktail) {
        dbTransaction(session -> {
            session.delete(cocktail);
            return "ok";
        });
    }
}
