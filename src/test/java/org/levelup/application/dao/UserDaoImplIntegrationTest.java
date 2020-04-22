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

import javax.persistence.NoResultException;
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

    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    UserEntity user = session.createQuery(
        "from UserEntity where name = :name and lastName = :lastName and passport = :passport", UserEntity.class
    ).setParameter("name", name)
        .setParameter("lastName", lastName)
        .setParameter("passport", passport)
        .getSingleResult();
    assertNotNull(user);
    UserAddressesEntity actualSherlockAddress = session.createQuery(
        "from UserAddressesEntity where user_id = :userId and address = :address", UserAddressesEntity.class
    ).setParameter("userId", user.getId())
        .setParameter("address", "221B Baker St.")
        .getSingleResult();
    assertNotNull(actualSherlockAddress);
    assertThrows(NoResultException.class, () -> session.createQuery(
        "from UserAddressesEntity where user_id = :userId and address = :address", UserAddressesEntity.class
        ).setParameter("userId", user.getId())
         .setParameter("address", "0000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
            "000000000000000000000000000000000")
         .getSingleResult()
    );
    session.remove(user);
    transaction.commit();
    session.close();

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

    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    UserEntity user = session.createQuery(
        "from UserEntity where name = :name and lastName = :lastName and passport = :passport", UserEntity.class
    ).setParameter("name", name)
        .setParameter("lastName", lastName)
        .setParameter("passport", passport)
        .getSingleResult();
    assertNotNull(user);
    UserAddressesEntity actualSherlockAddress = session.createQuery(
        "from UserAddressesEntity where user_id = :userId and address = :address", UserAddressesEntity.class
    ).setParameter("userId", user.getId())
        .setParameter("address", sherlockAddress.getAddress())
        .getSingleResult();
    assertNotNull(actualSherlockAddress);
    UserAddressesEntity actualPrimeMinisterAddress = session.createQuery(
        "from UserAddressesEntity where user_id = :userId and address = :address", UserAddressesEntity.class
    ).setParameter("userId", user.getId())
        .setParameter("address", primeMinisterAddress.getAddress())
        .getSingleResult();
    assertNotNull(actualPrimeMinisterAddress);
    session.remove(user);
    transaction.commit();
    session.close();
  }

}
