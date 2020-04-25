package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.CompanyEntity;
import org.levelup.application.domain.CompanyLegalDetailsEntity;
import org.levelup.configuration.HibernateTestConfiguration;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyLegalDetailsDaoImplIntegrationTest {

  private static SessionFactory factory;
  private static CompanyDao companyDao;
  private static CompanyLegalDetailsDao companyLegalDetailsDao;

  @BeforeAll
  public static void setupPositionDao() {
    factory = HibernateTestConfiguration.getFactory();
    companyDao = new CompanyDaoImpl(factory);
    companyLegalDetailsDao = new CompanyLegalDetailsDaoImpl(factory);
  }

  @AfterAll
  static void closeFactory() {
    HibernateTestConfiguration.close();
  }

  @Test
  @DisplayName("Update legal details: Bank name is null")
  public void testUpdateLegalDetailsInCompany_whenBankNameIsNull_thenThrowPersistenceException() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    String bic = "company bic";

    assertThrows(
        PersistenceException.class,
        () -> companyLegalDetailsDao.updateLegalDetailsInCompany(company.getId(), null, bic)
    );
    assertLegalDetailsDoNotExist(company.getId());
    clearEnvironment(company);
  }

  @Test
  @DisplayName("Update legal details: Bank name is too long")
  public void testUpdateLegalDetailsInCompany_whenBankNameIsToLong_thenThrowPersistenceException() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    String bankName = "company bank name company bank name company bank name company bank name company bank name " +
        "company bank name company bank name company bank name";
    String bic = "company bic";

    assertThrows(
        PersistenceException.class,
        () -> companyLegalDetailsDao.updateLegalDetailsInCompany(company.getId(), bankName, bic)
    );
    assertLegalDetailsDoNotExist(company.getId());
    clearEnvironment(company);
  }

  @Test
  @DisplayName("Update legal details: Bic is null")
  public void testUpdateLegalDetailsInCompany_whenBicIsNull_thenThrowPersistenceException() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    String bankName = "company bank name";

    assertThrows(
        PersistenceException.class,
        () -> companyLegalDetailsDao.updateLegalDetailsInCompany(company.getId(), bankName, null)
    );
    assertLegalDetailsDoNotExist(company.getId());
    clearEnvironment(company);
  }

  @Test
  @DisplayName("Update legal details: Bic is too long")
  public void testUpdateLegalDetailsInCompany_whenBicIsTooLong_thenThrowPersistenceException() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    String bankName = "company bank name";
    String bic = "000000000000000000000000000000000000000000000000000000000000";

    assertThrows(
        PersistenceException.class,
        () -> companyLegalDetailsDao.updateLegalDetailsInCompany(company.getId(), bankName, bic)
    );
    assertLegalDetailsDoNotExist(company.getId());
    clearEnvironment(company);
  }

  @Test
  @DisplayName("Update legal details: Company ID is null")
  public void testUpdateLegalDetailsInCompany_whenCompanyIdIsNull_thenThrowPersistenceException() {
    String bankName = "company bank name";
    String bic = "company bic";

    assertThrows(
        PersistenceException.class,
        () -> companyLegalDetailsDao.updateLegalDetailsInCompany(null, bankName, bic)
    );
  }

//  !!! This test des not work as expected. Real Hibernate throws PersistenceException here, H2 creates legal details.
//  !!! How to get this working?
//  @Test
//  @DisplayName("Update legal details: Company does not exist")
//  public void testUpdateLegalDetailsInCompany_whenCompanyDoesNotExist_thenThrowPersistenceException() {
//    Integer id = 1;
//    String bankName = "company bank name";
//    String bic = "company bic";
//
//    assertThrows(
//        PersistenceException.class, () -> companyLegalDetailsDao.updateLegalDetailsInCompany(id, bankName, bic)
//    );
//  }

  @Test
  @DisplayName("Update legal details: Company exists")
  public void testUpdateLegalDetailsInCompany_whenCompanyExists_thenPersistNewCompanyLegalDetails() {
    String bankName = "company bank name";
    String bic = "company bic";
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    companyLegalDetailsDao.updateLegalDetailsInCompany(company.getId(), bankName, bic);

    company = companyDao.findById(company.getId());
    assertLegalDetailsExist(company.getId());
    clearEnvironment(company);
  }

  @Test
  @DisplayName("Update legal details: Legal details already exist")
  public void testUpdateLegalDetailsInCompany_whenLegalDetailsExist_thenThrowPersistenceException() {
    String bankName = "company bank name";
    String bic = "company bic";
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    companyLegalDetailsDao.updateLegalDetailsInCompany(company.getId(), bankName, bic);

    assertThrows(
        PersistenceException.class,
        () -> companyLegalDetailsDao.updateLegalDetailsInCompany(company.getId(), bankName, bic)
    );

    CompanyEntity fetchedCompany = companyDao.findById(company.getId());
    clearEnvironment(fetchedCompany);
  }

  @Test
  @DisplayName("Find by bank name: Bank name is null")
  public void testFindAllByBankName_whenBankNameIsNull_returnEmptyCollection() {
    Collection<CompanyLegalDetailsEntity> actualResult = companyLegalDetailsDao.findAllByBankName(null);
    assertTrue(actualResult.isEmpty());
  }

  @Test
  @DisplayName("Find by bank name: Bank name is too long")
  public void testFindAllByBankName_whenBankNameIsTooLong_returnEmptyCollection() {
    String bankName = "company bank name company bank name company bank name company bank name company bank name " +
        "company bank name company bank name company bank name company bank name company bank name";
    Collection<CompanyLegalDetailsEntity> actualResult = companyLegalDetailsDao.findAllByBankName(bankName);
    assertTrue(actualResult.isEmpty());
  }

  @Test
  @DisplayName("Find by bank name: Bank name does not exist")
  public void testFindAllByBankName_whenBankNameDoesNotExist_returnEmptyCollection() {
    String bankName = "company bank name";
    Collection<CompanyLegalDetailsEntity> actualResult = companyLegalDetailsDao.findAllByBankName(bankName);
    assertTrue(actualResult.isEmpty());
  }

  @Test
  @DisplayName("Find by bank name: Bank name exists")
  public void testFindAllByBankName_whenBankNameDoesNotExist_returnLegalDetailsCollection() {
    String bankName = "company bank name";
    String bic = "company bic";
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    companyLegalDetailsDao.updateLegalDetailsInCompany(company.getId(), bankName, bic);
    List<CompanyLegalDetailsEntity> expectedResult = new ArrayList<>();
    CompanyLegalDetailsEntity legalDetails = new CompanyLegalDetailsEntity(company.getId(), bankName, bic);
    legalDetails.setCompany(company);
    expectedResult.add(legalDetails);

    Collection<CompanyLegalDetailsEntity> actualResult = companyLegalDetailsDao.findAllByBankName(bankName);
    assertEquals(expectedResult, actualResult);

    company = companyDao.findById(company.getId());
    clearEnvironment(company);
  }

  private void assertLegalDetailsExist(Integer companyId) {
    Session session = factory.openSession();
    List<CompanyLegalDetailsEntity> legalDetails = session.createQuery(
        "from CompanyLegalDetailsEntity where bankName = :bankName", CompanyLegalDetailsEntity.class
    ).setParameter("bankName", "company bank name")
        .getResultList();
    assertFalse(legalDetails.isEmpty());
    assertNotNull(legalDetails.get(0).getCompany());
    session.close();
    CompanyEntity company = companyDao.findById(companyId);
    assertNotNull(company.getLegalDetails());
  }

  private void assertLegalDetailsDoNotExist(Integer companyId) {
    Session session = factory.openSession();
    List<CompanyLegalDetailsEntity> legalDetails = session.createQuery(
        "from CompanyLegalDetailsEntity where bankName = :bankName", CompanyLegalDetailsEntity.class
    ).setParameter("bankName", "company bank name")
        .getResultList();
    assertTrue(legalDetails.isEmpty());
    session.close();
    CompanyEntity company = companyDao.findById(companyId);
    assertNull(company.getLegalDetails());
  }

  private void clearEnvironment(CompanyEntity company) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    session.remove(company);
    transaction.commit();
    session.close();
  }

}
