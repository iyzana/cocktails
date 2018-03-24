package de.randomerror.cocktails.backend.dao;

import de.randomerror.cocktails.backend.entity.CocktailOrder;

import java.util.List;
import java.util.Optional;

import static de.randomerror.cocktails.backend.dao.Hibernate.dbTransaction;

public class OrderDao {
    public static List<CocktailOrder> findAll() {
        return dbTransaction(session ->
                session.createQuery("select o from CocktailOrder o", CocktailOrder.class).list()
        );
    }

    public static Optional<CocktailOrder> findById(long id) {
        return dbTransaction(session ->
                Optional.ofNullable(session.find(CocktailOrder.class, id))
        );
    }

    public static List<CocktailOrder> findByRequester(String requester) {
        return dbTransaction(session ->
                session.createNamedQuery("CocktailOrder.findByRequester", CocktailOrder.class)
                        .setParameter("requester", requester)
                        .list()
        );
    }

    public static CocktailOrder save(CocktailOrder order) {
        return dbTransaction(session -> {
            session.save(order);
            return order;
        });
    }

    public static void delete(CocktailOrder order) {
        dbTransaction(session -> {
            session.delete(order);
            return "ok";
        });
    }
}
