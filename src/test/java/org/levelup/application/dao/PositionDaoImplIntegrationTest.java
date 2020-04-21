package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
  @DisplayName("Create new position when name does not exist")
  public void testCreatePosition_whenPositionWithNameNotExist_thenCreateNewPosition() {
    String name = "Java Developer";
    PositionEntity result = positionDao.createPosition(name);
    assertNotNull(result.getId());
    assertEquals(name, result.getName());

    Session session = factory.openSession();
    PositionEntity fromDb = session.createQuery("from PositionEntity where name = :name", PositionEntity.class)
        .setParameter("name", name)
        .getSingleResult();
    assertNotNull(fromDb);
    session.close();
  }

  @Test
  @DisplayName("Throw exception if name exists")
  public void testCreatePosition_whenPositionWithNameExists_thenThrowException() {
    String name = "PositionName";
    positionDao.createPosition(name);
    assertThrows(PersistenceException.class, () -> positionDao.createPosition(name));
  }

}
