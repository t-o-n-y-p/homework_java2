package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.levelup.application.domain.PositionEntity;
import org.levelup.configuration.HibernateTestConfiguration;

import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

public class PositionDaoImplIntegrationTest {

  private static SessionFactory factory;
  private static PositionDao positionDao;

  @BeforeAll
  public static void setupPositionDao() {
    factory = HibernateTestConfiguration.getFactory();
    positionDao = new PositionDaoImpl(factory);
  }

  @Test
  @DisplayName("Create: Position name too long")
  public void testCreatePosition_whenPositionNameTooLong_thenThrowPersistenceException() {
    String name = "position name position name position name position name " +
        "position name position name position name position name";
    assertThrows(PersistenceException.class, () -> positionDao.createPosition(name));
  }

  @Test
  @DisplayName("Create: Position is already added")
  public void testCreatePosition_whenPositionNameExists_thenThrowPersistenceException() {
    String name = "position name";
    positionDao.createPosition(name);
    assertThrows(PersistenceException.class, () -> positionDao.createPosition(name));

    clearEnvironment();
  }

  @Test
  @DisplayName("Create: Position name is null")
  public void testCreatePosition_whenPositionNameIsNull_thenThrowPersistenceException() {
    assertThrows(PersistenceException.class, () -> positionDao.createPosition(null));
  }

  @Test
  @DisplayName("Create: Position does not exist")
  public void testCreatePosition_whenPositionNameDoesNotExist_thenPersistNewPosition() {
    String name = "position name";
    PositionEntity actualResult = positionDao.createPosition(name);
    assertNotNull(actualResult.getId());
    assertEquals(name, actualResult.getName());

    clearEnvironment();
  }

  @Test
  @DisplayName("Find by name: Position name too long")
  public void testFindByName_whenNameTooLong_thenReturnNull() {
    String name = "position name position name position name position name " +
        "position name position name position name position name";
    PositionEntity actualResult = positionDao.findByName(name);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Find by name: Position name is null")
  public void testFindByName_whenNameIsNull_thenReturnNull() {
    PositionEntity actualResult = positionDao.findByName(null);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Find by name: Position does not exist")
  public void testFindByName_whenNameDoesNotExist_thenReturnNull() {
    String name = "position name";
    PositionEntity actualResult = positionDao.findByName(name);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Find by name: Position exists")
  public void testFindByName_whenNameExists_thenReturnPosition() {
    String name = "position name";
    PositionEntity expectedResult = positionDao.createPosition(name);
    PositionEntity actualResult = positionDao.findByName(name);
    assertEquals(expectedResult, actualResult);

    clearEnvironment();
  }

  @AfterAll
  static void closeFactory() {
    HibernateTestConfiguration.close();
  }

  private void clearEnvironment() {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    PositionEntity position = session.createQuery("from PositionEntity where name = :name", PositionEntity.class)
        .setParameter("name", "position name")
        .getSingleResult();
    assertNotNull(position);
    session.remove(position);
    transaction.commit();
    session.close();
  }

}
