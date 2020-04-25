package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.UserAddressesEntity;
import org.levelup.application.domain.UserEntity;
import org.levelup.configuration.HibernateTestConfiguration;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplIntegrationTest {

  private static SessionFactory factory;
  private static UserDao userDao;

  @BeforeAll
  public static void setupPositionDao() {
    factory = HibernateTestConfiguration.getFactory();
    userDao = new UserDaoImpl(factory);
  }

  @AfterAll
  static void closeFactory() {
    HibernateTestConfiguration.close();
  }

  @Test
  @DisplayName("Create user: Name is null")
  public void testCreateUser_whenNameIsNull_thenThrowPersistenceException() {
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> userDao.createUser(null, lastName, passport, addresses));

    assertUserDoesNotExist(null, lastName, passport);
  }

  @Test
  @DisplayName("Create user: Name is too long")
  public void testCreateUser_whenNameIsTooLong_thenThrowPersistenceException() {
    String tooLongName = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "00000000000000000000000000000000000000000000";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> userDao.createUser(tooLongName, lastName, passport, addresses));

    assertUserDoesNotExist(tooLongName, lastName, passport);
  }

  @Test
  @DisplayName("Create user: Last name is null")
  public void testCreateUser_whenLastNameIsNull_thenThrowPersistenceException() {
    String name = "user name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> userDao.createUser(name, null, passport, addresses));

    assertUserDoesNotExist(name, null, passport);
  }

  @Test
  @DisplayName("Create user: Last name is too long")
  public void testCreateUser_whenLastNameIsTooLong_thenThrowPersistenceException() {
    String name = "user name";
    String tooLongLastName = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> userDao.createUser(name, tooLongLastName, passport, addresses));

    assertUserDoesNotExist(name, tooLongLastName, passport);
  }

  @Test
  @DisplayName("Create user: Passport is null")
  public void testCreateUser_whenPassportIsNull_thenThrowPersistenceException() {
    String name = "user name";
    String lastName = "user last name";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> userDao.createUser(name, lastName, null, addresses));

    assertUserDoesNotExist(name, lastName, null);
  }

  @Test
  @DisplayName("Create user: Passport is too long")
  public void testCreateUser_whenPassportIsTooLong_thenThrowPersistenceException() {
    String name = "user name";
    String lastName = "user last name";
    String tooLongPassport = "000000000000000000000000000000000000000000000000000000000000000000000000000000";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> userDao.createUser(name, lastName, tooLongPassport, addresses));

    assertUserDoesNotExist(name, lastName, tooLongPassport);
  }

  @Test
  @DisplayName("Create user: Passport already exists")
  public void testCreateUser_whenPassportAlreadyExists_thenThrowPersistenceException() {
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    UserEntity user = userDao.createUser(name, lastName, passport, addresses);
    assertThrows(PersistenceException.class, () -> userDao.createUser(name, lastName, passport, addresses));

    clearEnvironment(user);
  }

  @Test
  @DisplayName("Create user: Collection of addresses is null")
  public void testCreateUser_whenCollectionOfAddressesIsNull_thenThrowNullPointerException() {
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    assertThrows(NullPointerException.class, () -> userDao.createUser(name, lastName, passport, null));

    assertUserDoesNotExist(name, lastName, passport);
  }

  @Test
  @DisplayName("Create user: Collection of addresses is empty")
  public void testCreateUser_whenCollectionOfAddressesIsEmpty_thenPersistNewUser() {
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>();

    UserEntity actualResult = userDao.createUser(name, lastName, passport, addresses);
    assertNotNull(actualResult.getId());
    assertEquals(name, actualResult.getName());
    assertEquals(lastName, actualResult.getLastName());
    assertEquals(passport, actualResult.getPassport());
    assertTrue(actualResult.getAddresses().isEmpty());

    clearEnvironment(actualResult);
  }

  @Test
  @DisplayName("Create user: Collection of addresses contains null")
  public void testCreateUser_whenCollectionOfAddressesContainsNull_thenThrowPersistenceException() {
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("10 Downing St.", null));

    assertThrows(PersistenceException.class, () -> userDao.createUser(name, lastName, passport, addresses));

    assertUserAddressExists("10 Downing St.");
    assertUserExistsAndClear();
  }

  @Test
  @DisplayName("Create user: Collection of addresses contains too long address")
  public void testCreateUser_whenCollectionOfAddressesContainsTooLongAddress_thenThrowPersistenceException() {
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(
        Arrays.asList("221B Baker St.", "0000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "000000000000000000000000000000000")
    );
    assertThrows(PersistenceException.class, () -> userDao.createUser(name, lastName, passport, addresses));

    assertUserAddressExists("221B Baker St.");
    assertUserExistsAndClear();
  }

  @Test
  @DisplayName("Create user: Valid params")
  public void testCreateUser_whenParamsAreValid_thenPersistNewUser() {
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    UserAddressesEntity sherlockAddress = new UserAddressesEntity();
    sherlockAddress.setAddress("221B Baker St.");
    UserAddressesEntity primeMinisterAddress = new UserAddressesEntity();
    primeMinisterAddress.setAddress("10 Downing St.");
    List<UserAddressesEntity> addressesEntities = new ArrayList<>(Arrays.asList(sherlockAddress, primeMinisterAddress));

    UserEntity actualResult = userDao.createUser(name, lastName, passport, addresses);
    assertNotNull(actualResult.getId());
    assertEquals(name, actualResult.getName());
    assertEquals(lastName, actualResult.getLastName());
    assertEquals(passport, actualResult.getPassport());
    assertEquals(
        addressesEntities.size(),
        actualResult.getAddresses().stream()
            .filter(a -> a.getId() != null)
            .count()
    );

    assertUserAddressExists(sherlockAddress.getAddress());
    assertUserAddressExists(primeMinisterAddress.getAddress());
    assertUserExistsAndClear();
  }

  private void clearEnvironment(UserEntity user) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    session.remove(user);
    transaction.commit();
    session.close();
  }

  private void assertUserExistsAndClear() {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    List<UserEntity> users = session.createQuery(
        "from UserEntity where name = :name and lastName = :lastName and passport = :passport", UserEntity.class
    ).setParameter("name", "user name")
        .setParameter("lastName", "user last name")
        .setParameter("passport", "user passport")
        .getResultList();
    session.remove(users.get(0));
    transaction.commit();
    session.close();
  }

  private void assertUserAddressExists(String address) {
    Session session = factory.openSession();
    List<UserEntity> users = session.createQuery(
        "from UserEntity where name = :name and lastName = :lastName and passport = :passport", UserEntity.class
    ).setParameter("name", "user name")
        .setParameter("lastName", "user last name")
        .setParameter("passport", "user passport")
        .getResultList();
    Integer userId = users.get(0).getId();
    List<UserAddressesEntity> addresses = session.createQuery(
        "from UserAddressesEntity where user_id = :userId and address = :address", UserAddressesEntity.class
    ).setParameter("userId", userId)
        .setParameter("address", address)
        .getResultList();
    assertFalse(addresses.isEmpty());
    session.close();
  }

  private void assertUserDoesNotExist(String name, String lastName, String passport) {
    Session session = factory.openSession();
    List<UserEntity> users = session.createQuery(
        "from UserEntity where name = :name and lastName = :lastName and passport = :passport", UserEntity.class
    ).setParameter("name", name)
        .setParameter("lastName", lastName)
        .setParameter("passport", passport)
        .getResultList();
    assertTrue(users.isEmpty());
    session.close();
  }

}
