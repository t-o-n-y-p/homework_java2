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

  @BeforeAll
  public static void beforeAll() {
    System.out.println("Before all");
  }

  @BeforeEach
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    when(factory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    when(session.createQuery(anyString(), eq(PositionEntity.class))).thenReturn(query);
    when(query.setParameter(eq("name"), anyString())).thenReturn(query);
    when(query.setParameter(eq("name"), eq(null))).thenReturn(query);
  }

  @Test
  public void testCreatePosition_whenPositionNameExists_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(PositionEntity.class));
    String name = "position name";
    assertThrows(PersistenceException.class, () -> dao.createPosition(name));
  }

  @Test
  public void testCreatePosition_whenPositionNameIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(PositionEntity.class));
    assertThrows(PersistenceException.class, () -> dao.createPosition(null));
  }

  @Test
  public void testCreatePosition_whenPositionNameDoesNotExist_persistNewPosition() {
    String name = "position name";
    PositionEntity entity = dao.createPosition(name);
    assertEquals(name, entity.getName());
    verify(session).persist(any(PositionEntity.class));
    verify(transaction, times(1)).commit();
    verify(session, times(1)).close();
  }

  @Test
  public void testFindByName_whenNameIsNull_returnNull() {
    List<PositionEntity> expectedResultList = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResultList);

    PositionEntity actualResult = dao.findByName(null);
    assertNull(actualResult);
    verify(session, times(1))
        .createQuery("from PositionEntity where name = :name", PositionEntity.class);
    verify(query, times(1)).setParameter("name", null);
    verify(query, times(1)).getResultList();
    verify(session, times(1)).close();

    when(query.getResultList()).thenReturn(null);
  }

  @Test
  public void testFindByName_whenNameDoesNotExist_returnNull() {
    String name = "position name";
    List<PositionEntity> expectedResultList = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResultList);

    PositionEntity actualResult = dao.findByName(name);
    assertNull(actualResult);
    verify(session, times(1))
        .createQuery("from PositionEntity where name = :name", PositionEntity.class);
    verify(query, times(1)).setParameter("name", name);
    verify(query, times(1)).getResultList();
    verify(session, times(1)).close();

    when(query.getResultList()).thenReturn(null);
  }

  @Test
  public void testFindByName_whenNameExists_persistNewPosition() {
    String name = "position name";
    List<PositionEntity> expectedResultList = new ArrayList<>();
    PositionEntity expectedResult = new PositionEntity();
    expectedResult.setName(name);
    expectedResultList.add(expectedResult);
    when(query.getResultList()).thenReturn(expectedResultList);

    PositionEntity actualResult = dao.findByName(name);
    assertEquals(expectedResult, actualResult);
    verify(session, times(1))
        .createQuery("from PositionEntity where name = :name", PositionEntity.class);
    verify(query, times(1)).setParameter("name", name);
    verify(query, times(1)).getResultList();
    verify(session, times(1)).close();

    when(query.getResultList()).thenReturn(null);
  }

  @AfterEach
  public void removeDao() {
    dao = null;
  }

  @AfterAll
  public static void afterAll() {
    System.out.println("After all");
  }

}