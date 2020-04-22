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