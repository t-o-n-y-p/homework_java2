package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.levelup.application.domain.PositionEntity;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PositionDaoImplTest {

  @Mock
  private SessionFactory factory;
  @Mock
  private Session session;
  @Mock
  private Transaction transaction;

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
  }

  @Test
  public void testCreatePosition_validParams_persistNewPosition() {
    String name = "position name";
    PositionEntity entity = dao.createPosition(name);
    assertEquals(name, entity.getName());
    verify(session).persist(ArgumentMatchers.any(PositionEntity.class));
    verify(transaction, times(1)).commit();
    verify(session, times(1)).close();
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