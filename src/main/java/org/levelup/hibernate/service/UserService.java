package org.levelup.hibernate.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.hibernate.domain.User;

public class UserService {

  private final SessionFactory factory;

  public UserService(SessionFactory factory) {
    this.factory = factory;
  }

  public User createUserPersist(String name, String lastName, String passport) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();

    User user = new User();  // transient
    user.setName(name);
    user.setLastName(lastName);
    user.setPassport(passport);

    session.persist(user);  // persistent

    transaction.commit();
    session.close(); // user - detached

    return user;
  }

  public Integer createUserSave(String name, String lastName, String passport) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();

    User user = new User();  // transient
    user.setName(name);
    user.setLastName(lastName);
    user.setPassport(passport);

    Integer generatedId = (Integer) session.save(user);

    transaction.commit();
    session.close();

    return generatedId;
  }

  public User getById(Integer id) {
    Session session = factory.openSession();
//    Transaction transaction = session.beginTransaction();

    User user = session.get(User.class, id);
    session.close();
    return user;

  }

  public Integer cloneUser(Integer id, String passport) {
    User user = getById(id);
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();

    user.setPassport(passport);
    Integer cloneId = (Integer) session.save(user);

    transaction.commit();
    session.close();

    return cloneId;
  }

  public User updateUserNameWithMerge(Integer id, String name) {
    User user = getById(id);
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();

    user.setName(name);
    User mergedUser = (User) session.merge(user);

    transaction.commit();
    session.close();

    System.out.println("original user: " + Integer.toHexString(user.hashCode()));
    return mergedUser;
  }

  public User mergeNewUser(String name, String lastName, String passport) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();

    User user = new User();  // transient
    user.setName(name);
    user.setLastName(lastName);
    user.setPassport(passport);

    User newUser = (User) session.merge(user);

    transaction.commit();
    session.close();

    return newUser;
  }

  public void updateUser(String name, String lastName, String passport) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();

    User user = new User();  // transient
    user.setName(name);
    user.setLastName(lastName);
    user.setPassport(passport);

    session.update(user);

    transaction.commit();
    session.close();

  }

}
