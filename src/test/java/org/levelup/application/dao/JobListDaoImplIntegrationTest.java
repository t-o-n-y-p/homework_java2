package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.*;
import org.levelup.configuration.HibernateTestConfiguration;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JobListDaoImplIntegrationTest {

  private static SessionFactory factory;
  private static CompanyDao companyDao;
  private static UserDao userDao;
  private static PositionDao positionDao;
  private static JobListDao jobListDao;

  @BeforeAll
  public static void setupPositionDao() {
    factory = HibernateTestConfiguration.getFactory();
    companyDao = new CompanyDaoImpl(factory);
    userDao = new UserDaoImpl(factory);
    positionDao = new PositionDaoImpl(factory);
    jobListDao = new JobListDaoImpl(factory);
  }

  @AfterAll
  static void closeFactory() {
    HibernateTestConfiguration.close();
  }

  @Test
  @DisplayName("Create job record: Company ID is null")
  public void testCreateJobRecord_whenCompanyIdIsNull_thenThrowPersistenceException() {
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class,
        () -> jobListDao.createJobRecord(null, user.getId(), position.getId(), startDate, endDate)
    );
    clearParentObjects(user, position);
  }

  @Test
  @DisplayName("Create job record: Company ID does not exist")
  public void testCreateJobRecord_whenCompanyIdDoesNotExist_thenThrowPersistenceException() {
    Integer companyId = 1;
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class,
        () -> jobListDao.createJobRecord(companyId, user.getId(), position.getId(), startDate, endDate)
    );
    clearParentObjects(user, position);
  }

  @Test
  @DisplayName("Create job record: User ID is null")
  public void testCreateJobRecord_whenUserIdIsNull_thenThrowPersistenceException() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    PositionEntity position = positionDao.createPosition("position name");
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class,
        () -> jobListDao.createJobRecord(company.getId(), null, position.getId(), startDate, endDate)
    );
    clearParentObjects(company, position);
  }

  @Test
  @DisplayName("Create job record: User ID does not exist")
  public void testCreateJobRecord_whenUserIdDoesNotExist_thenThrowPersistenceException() {
    Integer userId = 1;
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    PositionEntity position = positionDao.createPosition("position name");
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class,
        () -> jobListDao.createJobRecord(company.getId(), userId, position.getId(), startDate, endDate)
    );
    clearParentObjects(company, position);
  }

  @Test
  @DisplayName("Create job record: Position ID is null")
  public void testCreateJobRecord_whenPositionIdIsNull_thenThrowPersistenceException() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class,
        () -> jobListDao.createJobRecord(company.getId(), user.getId(), null, startDate, endDate)
    );
    clearParentObjects(company, user);
  }

  @Test
  @DisplayName("Create job record: Position ID does not exist")
  public void testCreateJobRecord_whenPositionIdDoesNotExist_thenThrowPersistenceException() {
    Integer positionId = 1;
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class,
        () -> jobListDao.createJobRecord(company.getId(), user.getId(), positionId, startDate, endDate)
    );
    clearParentObjects(company, user);
  }

  @Test
  @DisplayName("Create job record: Start date is null")
  public void testCreateJobRecord_whenStartDateIsNull_thenThrowPersistenceException() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class,
        () -> jobListDao.createJobRecord(company.getId(), user.getId(), position.getId(), null, endDate)
    );
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Create job record: Combination of the IDs already exists")
  public void testCreateJobRecord_whenCombinationOfIdsExists_thenThrowPersistenceException() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    jobListDao.createJobRecord(company.getId(), user.getId(), position.getId(), startDate, endDate);

    assertThrows(
        PersistenceException.class,
        () -> jobListDao.createJobRecord(company.getId(), user.getId(), position.getId(), startDate, endDate)
    );
    clearJobRecord(new JobListId(company.getId(), user.getId(), position.getId()));
    clearParentObjects(company, user, position);

  }

  @Test
  @DisplayName("Create job record: End date is null")
  public void testCreateJobRecord_whenEndDateIsNull_thenPersistJobRecord() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity expectedResult = new JobListEntity(
        new JobListId(company.getId(), user.getId(), position.getId()), startDate
    );

    JobListEntity actualResult = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, null
    );
    assertEquals(expectedResult, actualResult);
    clearJobRecord(expectedResult.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Create job record: End date is not null")
  public void testCreateJobRecord_whenEndDateIsNotNull_thenPersistJobRecord() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity expectedResult = new JobListEntity(
        new JobListId(company.getId(), user.getId(), position.getId()), startDate
    );
    expectedResult.setEndDate(endDate);

    JobListEntity actualResult = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );
    assertEquals(expectedResult, actualResult);
    clearJobRecord(expectedResult.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Find job record: Company ID is null")
  public void testFindJobRecord_whenCompanyIdIsNull_thenReturnNull() {
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");

    JobListEntity actualResult = jobListDao.findJobRecord(null, user.getId(), position.getId());
    assertNull(actualResult);
    clearParentObjects(user, position);
  }

  @Test
  @DisplayName("Find job record: Company ID does not exist")
  public void testFindJobRecord_whenCompanyIdDoesNotExist_thenReturnNull() {
    Integer companyId = 1;
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");

    JobListEntity actualResult = jobListDao.findJobRecord(companyId, user.getId(), position.getId());
    assertNull(actualResult);
    clearParentObjects(user, position);
  }

  @Test
  @DisplayName("Find job record: User ID is null")
  public void testFindJobRecord_whenUserIdIsNull_thenReturnNull() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    PositionEntity position = positionDao.createPosition("position name");

    JobListEntity actualResult = jobListDao.findJobRecord(company.getId(), null, position.getId());
    assertNull(actualResult);
    clearParentObjects(company, position);
  }

  @Test
  @DisplayName("Find job record: User ID does not exist")
  public void testFindJobRecord_whenUserIdDoesNotExist_thenReturnNull() {
    Integer userId = 1;
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    PositionEntity position = positionDao.createPosition("position name");

    JobListEntity actualResult = jobListDao.findJobRecord(company.getId(), userId, position.getId());
    assertNull(actualResult);
    clearParentObjects(company, position);
  }

  @Test
  @DisplayName("Find job record: Position ID is null")
  public void testFindJobRecord_whenPositionIdIsNull_thenReturnNull() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    JobListEntity actualResult = jobListDao.findJobRecord(company.getId(), user.getId(), null);
    assertNull(actualResult);
    clearParentObjects(company, user);
  }

  @Test
  @DisplayName("Find job record: Position ID does not exist")
  public void testFindJobRecord_whenPositionIdDoesNotExist_thenReturnNull() {
    Integer positionId = 1;
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    JobListEntity actualResult = jobListDao.findJobRecord(company.getId(), user.getId(), positionId);
    assertNull(actualResult);
    clearParentObjects(company, user);
  }

  @Test
  @DisplayName("Find job record: Combination of the IDs does not exist")
  public void testFindJobRecord_whenCombinationOfTheIdsDoesNotExist_thenReturnNull() {
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");

    JobListEntity actualResult = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    assertNull(actualResult);
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Find job record: Combination of the IDs exists")
  public void testFindJobRecord_whenCombinationOfTheIdsExists_thenReturnJobRecord() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity expectedResult = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    JobListEntity actualResult = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    assertEquals(expectedResult, actualResult);

    clearJobRecord(expectedResult.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Update job record: Company ID is null")
  public void testUpdateJobRecord_whenCompanyIdIsNull_thenReturnNull() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity jobRecord = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    JobListEntity actualResult = jobListDao.updateJobRecord(
        null, user.getId(), position.getId(), startDate, endDate
    );
    assertNull(actualResult);

    JobListEntity jobRecordAfterUpdate = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    assertEquals(jobRecord, jobRecordAfterUpdate);
    clearJobRecord(jobRecord.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Update job record: Company ID does not exist")
  public void testUpdateJobRecord_whenCompanyIdDoesNotExist_thenReturnNull() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity jobRecord = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    JobListEntity actualResult = jobListDao.updateJobRecord(
        company.getId() + 1, user.getId(), position.getId(), startDate, endDate
    );
    assertNull(actualResult);

    JobListEntity jobRecordAfterUpdate = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    assertEquals(jobRecord, jobRecordAfterUpdate);
    clearJobRecord(jobRecord.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Update job record: User ID is null")
  public void testUpdateJobRecord_whenUserIdIsNull_thenReturnNull() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity jobRecord = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    JobListEntity actualResult = jobListDao.updateJobRecord(
        company.getId(), null, position.getId(), startDate, endDate
    );
    assertNull(actualResult);

    JobListEntity jobRecordAfterUpdate = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    assertEquals(jobRecord, jobRecordAfterUpdate);
    clearJobRecord(jobRecord.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Update job record: User ID does not exist")
  public void testUpdateJobRecord_whenUserIdDoesNotExist_thenReturnNull() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity jobRecord = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    JobListEntity actualResult = jobListDao.updateJobRecord(
        company.getId(), user.getId() + 1, position.getId(), startDate, endDate
    );
    assertNull(actualResult);

    JobListEntity jobRecordAfterUpdate = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    assertEquals(jobRecord, jobRecordAfterUpdate);
    clearJobRecord(jobRecord.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Update job record: Position ID is null")
  public void testUpdateJobRecord_whenPositionIdIsNull_thenReturnNull() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity jobRecord = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    JobListEntity actualResult = jobListDao.updateJobRecord(
        company.getId(), user.getId(), null, startDate, endDate
    );
    assertNull(actualResult);

    JobListEntity jobRecordAfterUpdate = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    assertEquals(jobRecord, jobRecordAfterUpdate);
    clearJobRecord(jobRecord.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Update job record: Position ID does not exist")
  public void testUpdateJobRecord_whenPositionIdDoesNotExist_thenReturnNull() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity jobRecord = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    JobListEntity actualResult = jobListDao.updateJobRecord(
        company.getId(), user.getId(), position.getId() + 1, startDate, endDate
    );
    assertNull(actualResult);

    JobListEntity jobRecordAfterUpdate = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    assertEquals(jobRecord, jobRecordAfterUpdate);
    clearJobRecord(jobRecord.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Update job record: Combination of the IDs does not exist")
  public void testUpdateJobRecord_whenCombinationOfTheIdsDoesNotExist_thenReturnNull() {
    Integer companyId = 1;
    Integer userId = 1;
    Integer positionId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    JobListEntity actualResult = jobListDao.updateJobRecord(
        companyId, userId, positionId, startDate, endDate
    );
    assertNull(actualResult);

  }

  @Test
  @DisplayName("Update job record: Start date is null")
  public void testUpdateJobRecord_whenStartDateIsNull_thenThrowPersistenceException() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity jobRecord = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    assertThrows(
        PersistenceException.class,
        () -> jobListDao.updateJobRecord(company.getId(), user.getId(), position.getId(), null, endDate)
    );

    JobListEntity jobRecordAfterUpdate = jobListDao.findJobRecord(company.getId(), user.getId(), position.getId());
    assertEquals(jobRecord, jobRecordAfterUpdate);
    clearJobRecord(jobRecord.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Update job record: End date is null")
  public void testUpdateJobRecord_whenEndDateIsNull_thenReturnJobRecord() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate newStartDate = LocalDate.of(2021, 2, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity jobRecord = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    JobListEntity actualResult = jobListDao.updateJobRecord(
        company.getId(), user.getId(), position.getId(), newStartDate, null
    );

    assertEquals(newStartDate, actualResult.getStartDate());
    assertNull(actualResult.getEndDate());
    clearJobRecord(jobRecord.getId());
    clearParentObjects(company, user, position);
  }

  @Test
  @DisplayName("Update job record: End date is not null")
  public void testUpdateJobRecord_whenEndDateIsNotNull_thenReturnJobRecord() {
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate newStartDate = LocalDate.of(2021, 2, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    LocalDate newEndDate = LocalDate.of(2021, 2, 1);
    CompanyEntity company = companyDao.createCompany(
        "company name", "company ein", "company address"
    );
    UserEntity user = userDao.createUser(
        "user name", "user last name", "user passport", new ArrayList<>()
    );
    PositionEntity position = positionDao.createPosition("position name");
    JobListEntity jobRecord = jobListDao.createJobRecord(
        company.getId(), user.getId(), position.getId(), startDate, endDate
    );

    JobListEntity actualResult = jobListDao.updateJobRecord(
        company.getId(), user.getId(), position.getId(), newStartDate, newEndDate
    );

    assertEquals(newStartDate, actualResult.getStartDate());
    assertEquals(newEndDate, actualResult.getEndDate());
    clearJobRecord(jobRecord.getId());
    clearParentObjects(company, user, position);
  }

  private void clearParentObjects(CompanyEntity company, UserEntity user, PositionEntity position) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    session.remove(company);
    session.remove(user);
    session.remove(position);
    transaction.commit();
    session.close();
  }

  private void clearParentObjects(CompanyEntity company, UserEntity user) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    session.remove(company);
    session.remove(user);
    transaction.commit();
    session.close();
  }

  private void clearParentObjects(CompanyEntity company, PositionEntity position) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    session.remove(company);
    session.remove(position);
    transaction.commit();
    session.close();
  }

  private void clearParentObjects(UserEntity user, PositionEntity position) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    session.remove(user);
    session.remove(position);
    transaction.commit();
    session.close();
  }

  private void clearJobRecord(JobListId id) {
    Session session = factory.openSession();
    Transaction transaction = session.beginTransaction();
    JobListEntity jobRecord = session.get(JobListEntity.class, id);
    assertNotNull(jobRecord);
    session.remove(jobRecord);
    transaction.commit();
    session.close();
  }

}
