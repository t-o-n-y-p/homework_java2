package org.levelup.hibernate.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.hibernate.domain.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class UserService {

  private final SessionFactory factory;

  public UserService(SessionFactory factory) {
    this.factory = factory;
  }

  public User createUser(String name, String lastName, String passport) {
    User user;
    try (Session session = factory.openSession()) {
      user = new User();
      user.setName(name);
      user.setLastName(lastName);
      user.setPassport(passport);
      Transaction transaction = session.beginTransaction();
      session.persist(user);
      transaction.commit();
    }
    return user;
  }

  public User findByPassport(String passport) {
    User user;
    try (Session session = factory.openSession()) {
      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
      Root<User> userRoot = criteriaQuery.from(User.class);
      Predicate predicate = criteriaBuilder.equal(userRoot.get("passport"), passport);
      criteriaQuery
          .select(userRoot)
          .where(predicate);
      user = session.createQuery(criteriaQuery).uniqueResult();
    }
    return user;
  }

  public Collection<User> findByLastName(String lastName) {
    Collection<User> users;
    try (Session session = factory.openSession()) {
      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
      Root<User> userRoot = criteriaQuery.from(User.class);
      Predicate predicate = criteriaBuilder.equal(userRoot.get("lastName"), lastName);
      criteriaQuery
          .select(userRoot)
          .where(predicate);
      users = session.createQuery(criteriaQuery).getResultList();
    }
    return users;
  }

  public Collection<User> findByNameAndLastName(String name, String lastName) {
    Collection<User> users;
    try (Session session = factory.openSession()) {
      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
      Root<User> userRoot = criteriaQuery.from(User.class);
      Predicate predicate = criteriaBuilder.and(
          criteriaBuilder.equal(userRoot.get("name"), name),
          criteriaBuilder.equal(userRoot.get("lastName"), lastName)
      );
      criteriaQuery
          .select(userRoot)
          .where(predicate);
      users = session.createQuery(criteriaQuery).getResultList();
    }
    return users;
  }

  public void deleteByPassport(String passport) {
    User user = findByPassport(passport);
    try (Session session = factory.openSession()) {
      Transaction transaction = session.beginTransaction();
      session.remove(user);
      transaction.commit();
    }
  }

  public User updateUser(String passport, String newName, String newLastName) {
    User user = findByPassport(passport);
    try (Session session = factory.openSession()) {
      Transaction transaction = session.beginTransaction();
      session.load(User.class, user.getId());
      user.setName(newName);
      user.setLastName(newLastName);
      transaction.commit();
    }
    return user;
  }

}
