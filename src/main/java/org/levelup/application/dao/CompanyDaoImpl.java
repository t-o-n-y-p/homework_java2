package org.levelup.application.dao;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.levelup.application.domain.CompanyEntity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

public class CompanyDaoImpl extends AbstractDao implements CompanyDao {

  public CompanyDaoImpl(SessionFactory factory) {
    super(factory);
  }

  @Override
  public void create(String name, String ein, String address) {
    runInTransaction(s -> {
      CompanyEntity entity = new CompanyEntity();
      entity.setName(name);
      entity.setEin(ein);
      entity.setAddress(address);
      s.persist(entity);
    });
  }

  @Override
  public CompanyEntity findById(Integer id) {
//    Session session = factory.openSession();
//
//    CompanyEntity loadEntity = session.get(CompanyEntity.class, id);
//    System.out.println("Entity class: " + loadEntity.getClass().getName());
//
//    session.close();
//    System.out.println("Company name: " + loadEntity.getName());
    return runWithoutTransaction(s -> s.get(CompanyEntity.class, id));
  }

  @Override
  public CompanyEntity findByEin(String ein) {

    return runWithoutTransaction(s -> {
        return s.createQuery("from CompanyEntity where ein = :ein", CompanyEntity.class)
            .setParameter("ein", ein)
            .getSingleResult();
    });

  }

  @Override
  public CompanyEntity findByName(String name) {
    List<CompanyEntity> entities = runInTransaction(s -> {
      return s.createQuery("from CompanyEntity where name = :name", CompanyEntity.class)
          .setParameter("name", name)
          .getResultList();
    });
    return entities.isEmpty() ? null : entities.get(0);
  }

//  @SneakyThrows
//  private void performDatabaseOperation(Method method, Object object, Object... args) {
//
//    Session session = factory.openSession();
//    method.invoke(object, args);
//    session.close();
//
//  }
//
//  private <T> T performWithoutTransaction(DatabaseOperation<T> operation) {
//
//    Session session = factory.openSession();
//    T result = operation.doAction(session);
//    session.close();
//    return result;
//
//  }
//
//  @FunctionalInterface
//  interface DatabaseOperation<T> {
//
//    T doAction(Session session);
//
//  }
//
//
//  private <T> T perform(Function<Session, T> function) {
//    Session session = factory.openSession();
//    T result = function.apply(session);
//    session.close();
//    return result;
//  }

//  private <T> T runInTransaction(Function<Session, T> function) {
//    Session session = factory.openSession();
//    Transaction transaction = session.beginTransaction();
//    T result = function.apply(session);
//    transaction.commit();
//    session.close();
//    return result;
//  }


}
