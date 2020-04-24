package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.CompanyEntity;
import org.levelup.application.domain.PositionEntity;
import org.levelup.configuration.HibernateTestConfiguration;

import javax.persistence.PersistenceException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyDaoImplIntegrationTest {

  private static SessionFactory factory;
  private static CompanyDao companyDao;

  @BeforeAll
  public static void setupPositionDao() {
    factory = HibernateTestConfiguration.getFactory();
    companyDao = new CompanyDaoImpl(factory);
  }

  @AfterAll
  static void closeFactory() {
    HibernateTestConfiguration.close();
  }

  @Test
  @DisplayName("Create company: Name is null")
  public void testCreateCompany_whenCompanyNameIsNull_thenThrowPersistenceException() {
    String ein = "company ein";
    String address = "company address";
    assertThrows(PersistenceException.class, () -> companyDao.createCompany(null, ein, address));

    assertCompanyDoesNotExist();
  }

  @Test
  @DisplayName("Create company: Name is too long")
  public void testCreateCompany_whenCompanyNameIsTooLong_thenThrowPersistenceException() {
    String name = "company name company name company name company name company name company name company name " +
        "company name company name company name";
    String ein = "company ein";
    String address = "company address";
    assertThrows(PersistenceException.class, () -> companyDao.createCompany(name, ein, address));

    assertCompanyDoesNotExist();
  }

  @Test
  @DisplayName("Create company: Address is null")
  public void testCreateCompany_whenCompanyAddressIsNull_thenThrowPersistenceException() {
    String ein = "company ein";
    String name = "company name";
    assertThrows(PersistenceException.class, () -> companyDao.createCompany(name, ein, null));

    assertCompanyDoesNotExist();
  }

  @Test
  @DisplayName("Create company: Ein is null")
  public void testCreateCompany_whenCompanyEinIsNull_thenThrowPersistenceException() {
    String name = "company name";
    String address = "company address";
    assertThrows(PersistenceException.class, () -> companyDao.createCompany(name, null, address));

    assertCompanyDoesNotExist();
  }

  @Test
  @DisplayName("Create company: Ein is too long")
  public void testCreateCompany_whenCompanyEinIsTooLong_thenThrowPersistenceException() {
    String name = "company name";
    String ein = "company ein company ein company ein company ein company ein company ein company ein company ein";
    String address = "company address";
    assertThrows(PersistenceException.class, () -> companyDao.createCompany(name, ein, address));

    assertCompanyDoesNotExist();
  }

  @Test
  @DisplayName("Create company: Ein already exists")
  public void testCreateCompany_whenCompanyEinAlreadyExists_thenThrowPersistenceException() {
    String name = "company name";
    String ein = "company ein";
    String address = "company address";
    companyDao.createCompany(name, ein, address);
    assertThrows(PersistenceException.class, () -> companyDao.createCompany(name, ein, address));

    clearEnvironment();
  }

  @Test
  @DisplayName("Create company: Ein is not added")
  public void testCreateCompany_whenEinIsNotAdded_thenPersistNewCompany() {
    String name = "company name";
    String ein = "company ein";
    String address = "company address";
    CompanyEntity actualResult = companyDao.createCompany(name, ein, address);
    assertNotNull(actualResult.getId());
    assertEquals(name, actualResult.getName());
    assertEquals(ein, actualResult.getEin());
    assertEquals(address, actualResult.getAddress());

    clearEnvironment();
  }

  @Test
  @DisplayName("Find by ID: ID is null")
  public void testFindById_whenIdIsNull_thenThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> companyDao.findById(null));
  }

  private void clearEnvironment() {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    CompanyEntity company = session.createQuery("from CompanyEntity where ein = :ein", CompanyEntity.class)
        .setParameter("ein", "company ein")
        .getSingleResult();
    assertNotNull(company);
    session.remove(company);
    transaction.commit();
    session.close();
  }

  private void assertCompanyDoesNotExist() {
    Session session = factory.openSession();
    List<CompanyEntity> companies = session.createQuery(
        "from CompanyEntity where ein = :ein", CompanyEntity.class
    ).setParameter("ein", "company ein")
        .getResultList();
    assertTrue(companies.isEmpty());
    session.close();
  }

}
