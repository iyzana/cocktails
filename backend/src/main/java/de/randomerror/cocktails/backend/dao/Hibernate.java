package de.randomerror.cocktails.backend.dao;

import de.randomerror.cocktails.backend.App;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Function;

public class Hibernate {
    static <T> T dbTransaction(Function<Session, T> function) {
        Transaction tx = App.session.beginTransaction();
        try {
            T result = function.apply(App.session);
            tx.commit();
            return result;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
