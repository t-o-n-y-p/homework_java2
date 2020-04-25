package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.CompanyEntity;
import org.levelup.configuration.HibernateTestConfiguration;

import javax.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;
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
    CompanyEntity company = companyDao.createCompany(name, ein, address);
    assertThrows(PersistenceException.class, () -> companyDao.createCompany(name, ein, address));

    clearEnvironment(company);
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

    clearEnvironment(actualResult);
  }

  @Test
  @DisplayName("Find by ID: ID is null")
  public void testFindById_whenIdIsNull_thenThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> companyDao.findById(null));
  }

  @Test
  @DisplayName("Find by ID: ID does not exist")
  public void testFindById_whenIdDoesNotExist_thenReturnNull() {
    Integer id = 1;
    CompanyEntity actualResult = companyDao.findById(id);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Find by ID: ID exists")
  public void testFindById_whenIdExists_thenReturnCompany() {
    CompanyEntity expectedResult = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    Integer id = expectedResult.getId();
    CompanyEntity actualResult = companyDao.findById(id);
    assertEquals(expectedResult, actualResult);

    clearEnvironment(actualResult);
  }

  @Test
  @DisplayName("Find by ein: Ein is null")
  public void testFindByEin_whenEinIsNull_thenReturnNull() {
    CompanyEntity actualResult = companyDao.findByEin(null);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Find by ein: Ein does not exist")
  public void testFindByEin_whenEinDoesNotExist_thenReturnNull() {
    String ein = "company ein";
    CompanyEntity actualResult = companyDao.findByEin(ein);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Find by ein: Ein is too long")
  public void testFindByEin_whenEinIsTooLong_thenReturnNull() {
    String ein = "company ein company ein company ein company ein company ein company ein company ein company ein";
    CompanyEntity actualResult = companyDao.findByEin(ein);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Find by ein: Ein exists")
  public void testFindByEin_whenEinExists_thenReturnCompany() {
    String ein = "company ein";
    CompanyEntity expectedResult = companyDao.createCompany("company name", ein, "company address");
    CompanyEntity actualResult = companyDao.findByEin(ein);
    assertEquals(expectedResult, actualResult);

    clearEnvironment(actualResult);
  }

  @Test
  @DisplayName("Find by name: Name is null")
  public void testFindByName_whenNameIsNull_thenReturnEmptyCollection() {
    Collection<CompanyEntity> actualResult = companyDao.findByName(null);
    assertTrue(actualResult.isEmpty());
  }

  @Test
  @DisplayName("Find by name: Name does not exist")
  public void testFindByName_whenNameDoesNotExist_thenReturnEmptyCollection() {
    String name = "company name";
    Collection<CompanyEntity> actualResult = companyDao.findByName(name);
    assertTrue(actualResult.isEmpty());
  }

  @Test
  @DisplayName("Find by name: Name is too long")
  public void testFindByName_whenNameIsTooLong_thenReturnEmptyCollection() {
    String name = "company name company name company name company name company name company name " +
        "company name company name company name";
    Collection<CompanyEntity> actualResult = companyDao.findByName(name);
    assertTrue(actualResult.isEmpty());
  }

  @Test
  @DisplayName("Find by name: Name exists")
  public void testFindByName_whenNameExists_thenReturnCompanyCollection() {
    String name = "company name";
    CompanyEntity company = companyDao.createCompany(name, "company ein", "company address");
    Collection<CompanyEntity> expectedResult = new ArrayList<>();
    expectedResult.add(company);

    Collection<CompanyEntity> actualResult = companyDao.findByName(name);
    assertEquals(expectedResult, actualResult);

    clearEnvironment(company);
  }

  @Test
  @DisplayName("Update company by ein: Ein is null")
  public void testUpdateCompany_whenEinIsNull_thenReturnNull() {
    String name = "company name";
    String address = "company address";
    CompanyEntity actualResult = companyDao.updateCompany(null, name, address);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Update company by ein: Ein does not exist")
  public void testUpdateCompany_whenEinDoesNotExist_thenReturnNull() {
    String ein = "company ein";
    String name = "company name";
    String address = "company address";
    CompanyEntity actualResult = companyDao.updateCompany(ein, name, address);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Update company by ein: Ein is too long")
  public void testUpdateCompany_whenEinIsTooLong_thenReturnNull() {
    String ein = "company ein company ein company ein company ein company ein company ein company ein";
    String name = "company name";
    String address = "company address";
    CompanyEntity actualResult = companyDao.updateCompany(ein, name, address);
    assertNull(actualResult);
  }

  @Test
  @DisplayName("Update company by ein: Ein exists")
  public void testUpdateCompany_whenEinExists_thenMergeCompany() {
    String ein = "company ein";
    String name = "company name";
    String address = "company address";
    CompanyEntity company = companyDao.createCompany("name", ein, "address");
    CompanyEntity expectedResult = new CompanyEntity();
    expectedResult.setId(company.getId());
    expectedResult.setName(name);
    expectedResult.setEin(ein);
    expectedResult.setAddress(address);

    CompanyEntity actualResult = companyDao.updateCompany(ein, name, address);
    assertEquals(expectedResult, actualResult);

    clearEnvironment(actualResult);
  }

  @Test
  @DisplayName("Update company by ein: Name is null")
  public void testUpdateCompany_whenNameIsNull_thenThrowPersistenceException() {
    String ein = "company ein";
    String address = "company address";
    CompanyEntity company = companyDao.createCompany("name", ein, "address");

    assertThrows(PersistenceException.class, () -> companyDao.updateCompany(ein, null, address));

    CompanyEntity fetchedCompany = companyDao.findById(company.getId());
    assertEquals(company, fetchedCompany);
    clearEnvironment(company);
  }

  @Test
  @DisplayName("Update company by ein: Name is too long")
  public void testUpdateCompany_whenNameIsTooLong_thenThrowPersistenceException() {
    String ein = "company ein";
    String name = "company name company name company name company name company name company name " +
        "company name company name company name company name company name";
    String address = "company address";
    CompanyEntity company = companyDao.createCompany("name", ein, "address");

    assertThrows(PersistenceException.class, () -> companyDao.updateCompany(ein, name, address));

    CompanyEntity fetchedCompany = companyDao.findById(company.getId());
    assertEquals(company, fetchedCompany);
    clearEnvironment(company);
  }

  @Test
  @DisplayName("Update company by ein: Address is null")
  public void testUpdateCompany_whenAddressIsNull_thenThrowPersistenceException() {
    String ein = "company ein";
    String name = "company name";
    CompanyEntity company = companyDao.createCompany("name", ein, "address");

    assertThrows(PersistenceException.class, () -> companyDao.updateCompany(ein, name, null));

    CompanyEntity fetchedCompany = companyDao.findById(company.getId());
    assertEquals(company, fetchedCompany);
    clearEnvironment(company);
  }

  private void clearEnvironment(CompanyEntity company) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
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
