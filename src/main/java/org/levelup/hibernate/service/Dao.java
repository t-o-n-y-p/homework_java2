package org.levelup.hibernate.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.hibernate.JobSessionFactoryConfiguration;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Dao<T> {

  SessionFactory factory = JobSessionFactoryConfiguration.getFactory();

  default T runInTransactionWithSingleResult(Function<Session, T> function) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    T result = function.apply(session);
    transaction.commit();
    session.close();
    return result;
  }

  default List<T> runInTransaction(Function<Session, List<T>> function) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    List<T> result = function.apply(session);
    transaction.commit();
    session.close();
    return result;
  }

  default List<T> run(Function<Session, List<T>> function) {
    Session session = factory.openSession();
    List<T> result = function.apply(session);
    session.close();
    return result;
  }

  default T runWithSingleResult(Function<Session, T> function) {
    Session session = factory.openSession();
    T result = function.apply(session);
    session.close();
    return result;
  }

  default void delete(Consumer<Session> function) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    function.accept(session);
    transaction.commit();
    session.close();
  }

  default void update(Consumer<Session> function) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    function.accept(session);
    transaction.commit();
    session.close();
  }

}
