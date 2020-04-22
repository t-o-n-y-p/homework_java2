package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.AuthDetailsEntity;
import org.levelup.application.domain.UserEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class AuthDetailsDaoImplTest {

  @Mock
  private SessionFactory factory;
  @Mock
  private Session session;
  @Mock
  private Transaction transaction;
  @Mock
  private Query<AuthDetailsEntity> query;

  @InjectMocks
  private AuthDetailsDaoImpl dao;

  @BeforeEach
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    when(factory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    when(session.createQuery(anyString(), eq(AuthDetailsEntity.class))).thenReturn(query);
    when(query.setParameter(eq("login"), anyString())).thenReturn(query);
    when(query.setParameter(eq("login"), eq(null))).thenReturn(query);
    when(query.setParameter(eq("password"), anyString())).thenReturn(query);
    when(query.setParameter(eq("password"), eq(null))).thenReturn(query);
  }

  @AfterEach
  public void removeDao() {
    dao = null;
  }

  @Test
  @DisplayName("Log in: Password is too long")
  public void testLogIn_whenPasswordIsTooLong_thenReturnNull() {
    List<AuthDetailsEntity> authDetailsEntities = new ArrayList<>();
    when(query.getResultList()).thenReturn(authDetailsEntities);

    String login = "login";
    String password = "password password password password password password password password " +
        "password password password password";
    UserEntity actualResult = dao.logIn(login, password);
    assertNull(actualResult);

    verifyFindByLoginAndPasswordCalls(login, password);
  }

  @Test
  @DisplayName("Log in: Login is too long")
  public void testLogIn_whenLoginIsTooLong_thenReturnNull() {
    List<AuthDetailsEntity> authDetailsEntities = new ArrayList<>();
    when(query.getResultList()).thenReturn(authDetailsEntities);

    String login = "login login login login login login login login login login login " +
        "login login login login login login login";
    String password = "password";
    UserEntity actualResult = dao.logIn(login, password);
    assertNull(actualResult);

    verifyFindByLoginAndPasswordCalls(login, password);
  }

  @Test
  @DisplayName("Log in: Password is null")
  public void testLogIn_whenPasswordIsNull_thenReturnNull() {
    List<AuthDetailsEntity> authDetailsEntities = new ArrayList<>();
    when(query.getResultList()).thenReturn(authDetailsEntities);

    String login = "login";
    UserEntity actualResult = dao.logIn(login, null);
    assertNull(actualResult);

    verifyFindByLoginAndPasswordCalls(login, null);
  }

  @Test
  @DisplayName("Log in: Login is null")
  public void testLogIn_whenLoginIsNull_thenReturnNull() {
    List<AuthDetailsEntity> authDetailsEntities = new ArrayList<>();
    when(query.getResultList()).thenReturn(authDetailsEntities);

    String password = "password";
    UserEntity actualResult = dao.logIn(null, password);
    assertNull(actualResult);

    verifyFindByLoginAndPasswordCalls(null, password);
  }

  @Test
  @DisplayName("Log in: User does not exist")
  public void testLogIn_whenUserDoesNotExist_thenReturnNull() {
    List<AuthDetailsEntity> authDetailsEntities = new ArrayList<>();
    when(query.getResultList()).thenReturn(authDetailsEntities);

    String login = "login";
    String password = "password";
    UserEntity actualResult = dao.logIn(login, password);
    assertNull(actualResult);

    verifyFindByLoginAndPasswordCalls(login, password);
  }

  @Test
  @DisplayName("Log in: Existing user")
  public void testLogIn_whenUserExists_thenReturnUser() {
    List<AuthDetailsEntity> authDetailsEntities = new ArrayList<>();
    AuthDetailsEntity authDetails = new AuthDetailsEntity();
    UserEntity expectedResult = new UserEntity();
    authDetails.setUser(expectedResult);
    authDetailsEntities.add(authDetails);
    when(query.getResultList()).thenReturn(authDetailsEntities);

    String login = "login";
    String password = "password";
    UserEntity actualResult = dao.logIn(login, password);
    assertEquals(expectedResult, actualResult);

    verifyFindByLoginAndPasswordCalls(login, password);
  }

  private void verifyFindByLoginAndPasswordCalls(String login, String password) {
    verify(session, times(1)).createQuery(
        eq("from AuthDetailsEntity where login = :login and password = :password"), eq(AuthDetailsEntity.class)
    );
    verify(query, times(1)).setParameter(eq("login"), eq(login));
    verify(query, times(1)).setParameter(eq("password"), eq(password));
    verify(query, times(1)).getResultList();
    verify(session, times(1)).close();
  }


}