package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.levelup.application.domain.PositionEntity;
import org.mockito.*;

import javax.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PositionDaoImplTest {

  @Mock
  private SessionFactory factory;
  @Mock
  private Session session;
  @Mock
  private Transaction transaction;
  @Mock
  private Query<PositionEntity> query;

  @InjectMocks
  private PositionDaoImpl dao;

  @BeforeEach
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    when(factory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    when(session.createQuery(anyString(), eq(PositionEntity.class))).thenReturn(query);
    when(query.setParameter(eq("name"), anyString())).thenReturn(query);
    when(query.setParameter(eq("name"), eq(null))).thenReturn(query);
  }

  @AfterEach
  public void removeDao() {
    dao = null;
  }

  @Test
  @DisplayName("Create: Position name too long")
  public void testCreatePosition_whenPositionNameTooLong_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(PositionEntity.class));
    String name = "position name position name position name position name " +
        "position name position name position name position name";
    assertThrows(PersistenceException.class, () -> dao.createPosition(name));
  }

  @Test
  @DisplayName("Create: Position is already added")
  public void testCreatePosition_whenPositionNameExists_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(PositionEntity.class));
    String name = "position name";
    assertThrows(PersistenceException.class, () -> dao.createPosition(name));
  }

  @Test
  @DisplayName("Create: Position name is null")
  public void testCreatePosition_whenPositionNameIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(PositionEntity.class));
    assertThrows(PersistenceException.class, () -> dao.createPosition(null));
  }

  @Test
  @DisplayName("Create: Position does not exist")
  public void testCreatePosition_whenPositionNameDoesNotExist_thenPersistNewPosition() {
    String name = "position name";
    PositionEntity entity = dao.createPosition(name);
    assertEquals(name, entity.getName());
    verify(session).persist(any(PositionEntity.class));
    verify(transaction, times(1)).commit();
    verify(session, times(1)).close();
  }

  @Test
  @DisplayName("Find by name: Position name too long")
  public void testFindByName_whenNameTooLong_thenReturnNull() {
    String name = "position name position name position name position name " +
        "position name position name position name position name";
    List<PositionEntity> expectedResultList = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResultList);

    PositionEntity actualResult = dao.findByName(name);
    assertNull(actualResult);
    verifyFindByNameCalls(name);
  }

  @Test
  @DisplayName("Find by name: Position name is null")
  public void testFindByName_whenNameIsNull_thenReturnNull() {
    List<PositionEntity> expectedResultList = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResultList);

    PositionEntity actualResult = dao.findByName(null);
    assertNull(actualResult);
    verifyFindByNameCalls(null);
  }

  @Test
  @DisplayName("Find by name: Position does not exist")
  public void testFindByName_whenNameDoesNotExist_thenReturnNull() {
    String name = "position name";
    List<PositionEntity> expectedResultList = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResultList);

    PositionEntity actualResult = dao.findByName(name);
    assertNull(actualResult);
    verifyFindByNameCalls(name);
  }

  @Test
  @DisplayName("Find by name: Position exists")
  public void testFindByName_whenNameExists_thenReturnPosition() {
    String name = "position name";
    List<PositionEntity> expectedResultList = new ArrayList<>();
    PositionEntity expectedResult = new PositionEntity();
    expectedResult.setName(name);
    expectedResultList.add(expectedResult);
    when(query.getResultList()).thenReturn(expectedResultList);

    PositionEntity actualResult = dao.findByName(name);
    assertEquals(expectedResult, actualResult);
    verifyFindByNameCalls(name);
  }

  private void verifyFindByNameCalls(String name) {
    verify(session, times(1))
        .createQuery(eq("from PositionEntity where name = :name"), eq(PositionEntity.class));
    verify(query, times(1)).setParameter(eq("name"), eq(name));
    verify(query, times(1)).getResultList();
    verify(session, times(1)).close();
  }

}