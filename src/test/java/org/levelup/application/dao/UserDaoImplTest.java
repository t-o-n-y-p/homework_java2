package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.UserAddressesEntity;
import org.levelup.application.domain.UserEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserDaoImplTest {

  @Mock
  private SessionFactory factory;
  @Mock
  private Session session;
  @Mock
  private Transaction transaction;

  @InjectMocks
  private UserDaoImpl dao;

  @BeforeEach
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    when(factory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
  }

  @AfterEach
  public void removeDao() {
    dao = null;
  }

  @Test
  @DisplayName("Create user: Name is null")
  public void testCreateUser_whenNameIsNull_thenThrowNullPointerException() {
    doThrow(NullPointerException.class).when(session).persist(any(UserEntity.class));
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(NullPointerException.class, () -> dao.createUser(null, lastName, passport, addresses));
  }

  @Test
  @DisplayName("Create user: Name is too long")
  public void testCreateUser_whenNameIsTooLong_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(UserEntity.class));
    String tooLongName = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "00000000000000000000000000000000000000000000";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> dao.createUser(tooLongName, lastName, passport, addresses));
  }

  @Test
  @DisplayName("Create user: Last name is null")
  public void testCreateUser_whenLastNameIsNull_thenThrowNullPointerException() {
    doThrow(NullPointerException.class).when(session).persist(any(UserEntity.class));
    String name = "user name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(NullPointerException.class, () -> dao.createUser(name, null, passport, addresses));
  }

  @Test
  @DisplayName("Create user: Last name is too long")
  public void testCreateUser_whenLastNameIsTooLong_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(UserEntity.class));
    String name = "user name";
    String tooLongLastName = "00000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> dao.createUser(name, tooLongLastName, passport, addresses));
  }

  @Test
  @DisplayName("Create user: Passport is null")
  public void testCreateUser_whenPassportIsNull_thenThrowNullPointerException() {
    doThrow(NullPointerException.class).when(session).persist(any(UserEntity.class));
    String name = "user name";
    String lastName = "user last name";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(NullPointerException.class, () -> dao.createUser(name, lastName, null, addresses));
  }

  @Test
  @DisplayName("Create user: Passport is too long")
  public void testCreateUser_whenPassportIsTooLong_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(UserEntity.class));
    String name = "user name";
    String lastName = "user last name";
    String tooLongPassport = "000000000000000000000000000000000000000000000000000000000000000000000000000000";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> dao.createUser(name, lastName, tooLongPassport, addresses));
  }

  @Test
  @DisplayName("Create user: Passport already exists")
  public void testCreateUser_whenPassportAlreadyExists_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(UserEntity.class));
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList("221B Baker St.", "10 Downing St."));
    assertThrows(PersistenceException.class, () -> dao.createUser(name, lastName, passport, addresses));
  }

  @Test
  @DisplayName("Create user: Collection of addresses is null")
  public void testCreateUser_whenCollectionOfAddressesIsNull_thenThrowNullPointerException() {
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    assertThrows(NullPointerException.class, () -> dao.createUser(name, lastName, passport, null));
  }

  @Test
  @DisplayName("Create user: Collection of addresses is empty")
  public void testCreateUser_whenCollectionOfAddressesIsEmpty_thenPersistNewUser() {
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>();

    UserEntity actualResult = dao.createUser(name, lastName, passport, addresses);
    assertEquals(name, actualResult.getName());
    assertEquals(lastName, actualResult.getLastName());
    assertEquals(passport, actualResult.getPassport());
    assertTrue(actualResult.getAddresses().isEmpty());

    verify(session).persist(any(UserEntity.class));
    verify(transaction, times(1)).commit();
    verify(session, times(1)).close();
  }

  @Test
  @DisplayName("Create user: Collection of addresses contains null")
  public void testCreateUser_whenCollectionOfAddressesContainsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(UserEntity.class));
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    Collection<String> addresses = new ArrayList<>(Arrays.asList(null, "10 Downing St."));

    assertThrows(PersistenceException.class, () -> dao.createUser(name, lastName, passport, addresses));
  }

  @Test
  @DisplayName("Create user: Collection of addresses contains too long address")
  public void testCreateUser_whenCollectionOfAddressesContainsTooLongAddress_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(UserEntity.class));
    String name = "user name";
    String lastName = "user last name";
    String passport = "user passport";
    String tooLongAddress = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
        "000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
    Collection<String> addresses = new ArrayList<>(Arrays.asList(tooLongAddress, "10 Downing St."));

    assertThrows(PersistenceException.class, () -> dao.createUser(name, lastName, passport, addresses));
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

    UserEntity actualResult = dao.createUser(name, lastName, passport, addresses);
    assertEquals(name, actualResult.getName());
    assertEquals(lastName, actualResult.getLastName());
    assertEquals(passport, actualResult.getPassport());
    assertEquals(addressesEntities, actualResult.getAddresses());

    verify(session).persist(any(UserEntity.class));
    verify(transaction, times(1)).commit();
    verify(session, times(1)).close();
  }

}