package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.AuthDetailsEntity;
import org.levelup.application.domain.UserEntity;
import org.levelup.configuration.HibernateTestConfiguration;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDetailsDaoImplIntegrationTest {

  private static SessionFactory factory;
  private static AuthDetailsDao authDetailsDao;

  @BeforeAll
  public static void setupPositionDao() {
    factory = HibernateTestConfiguration.getFactory();
    authDetailsDao = new AuthDetailsDaoImpl(factory);
  }

  @AfterAll
  static void closeFactory() {
    HibernateTestConfiguration.close();
  }

  @Test
  @DisplayName("Log in: Password is too long")
  public void testLogIn_whenPasswordIsTooLong_thenReturnNull() {
    String login = "user login";
    String password = "user password user password user password user password user password " +
        "user password user password user password";

    UserEntity actualResult = authDetailsDao.logIn(login, password);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Log in: Login is too long")
  public void testLogIn_whenLoginIsTooLong_thenReturnNull() {
    String login = "user login user login user login user login user login user login " +
        "user login user login user login user login user login";
    String password = "user password";

    UserEntity actualResult = authDetailsDao.logIn(login, password);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Log in: Password is null")
  public void testLogIn_whenPasswordIsNull_thenReturnNull() {
    String login = "user login";

    UserEntity actualResult = authDetailsDao.logIn(login, null);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Log in: Login is null")
  public void testLogIn_whenLoginIsNull_thenReturnNull() {
    String password = "user password";

    UserEntity actualResult = authDetailsDao.logIn(null, password);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Log in: User does not exist")
  public void testLogIn_whenUserDoesNotExist_thenReturnNull() {
    String login = "user login";
    String password = "user password";

    UserEntity actualResult = authDetailsDao.logIn(login, password);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Log in: Existing user")
  public void testLogIn_whenUserExists_thenReturnUser() {
    String login = "user login";
    String password = "user password";
    UserEntity expectedResult = prepareEnvironment();

    UserEntity actualResult = authDetailsDao.logIn(login, password);
    assertEquals(expectedResult, actualResult);

    clearEnvironment();
  }

  private void clearEnvironment() {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    AuthDetailsEntity authDetails = session.createQuery(
        "from AuthDetailsEntity where login = :login and password = :password", AuthDetailsEntity.class
    ).setParameter("login", "user login")
        .setParameter("password", "user password")
        .getSingleResult();
    assertNotNull(authDetails);
    session.remove(authDetails);
    transaction.commit();
    session.close();
  }

  private UserEntity prepareEnvironment() {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();

    UserEntity user = new UserEntity();
    user.setName("user name");
    user.setLastName("user last name");
    user.setPassport("user passport");
    user.setAddresses(new ArrayList<>());

    AuthDetailsEntity authDetails = new AuthDetailsEntity();
    authDetails.setLogin("user login");
    authDetails.setPassword("user password");
    authDetails.setUser(user);

    session.persist(authDetails);

    transaction.commit();
    session.close();

    return user;
  }

}
