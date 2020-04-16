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
  public CompanyEntity createCompany(String name, String ein, String address) {
    return runInTransaction(s -> {
      CompanyEntity entity = new CompanyEntity();
      entity.setName(name);
      entity.setEin(ein);
      entity.setAddress(address);
      s.persist(entity);
      return entity;
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
    List<CompanyEntity> entities = runWithoutTransaction(
        s -> s.createQuery("from CompanyEntity where ein = :ein", CompanyEntity.class)
            .setParameter("ein", ein)
            .getResultList()
    );
    return entities.isEmpty() ? null : entities.get(0);
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

  @Override
  public CompanyEntity updateCompany(String ein, String name, String address) {
    CompanyEntity company = findByEin(ein);
    return updateCompany(company, name, address);
  }

  @Override
  public CompanyEntity updateCompany(CompanyEntity company, String name, String address) {
    if (company == null) {
      return null;
    }
    return runInTransaction(s -> {
      company.setName(name);
      company.setAddress(address);
      return (CompanyEntity) s.merge(company);
    });
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
