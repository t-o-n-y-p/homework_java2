package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.JobListEntity;
import org.levelup.application.domain.JobListId;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class JobListDaoImplTest {

  @Mock
  private SessionFactory factory;
  @Mock
  private Session session;
  @Mock
  private Transaction transaction;

  @InjectMocks
  private JobListDaoImpl dao;

  @BeforeEach
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    when(factory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
  }

  @AfterEach
  public void removeDao() {
    dao = null;
  }

  @Test
  @DisplayName("Create job record: Company ID is null")
  public void testCreateJobRecord_whenCompanyIdIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer userId = 1;
    Integer positionId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class, () -> dao.createJobRecord(null, userId, positionId, startDate, endDate)
    );
  }

  @Test
  @DisplayName("Create job record: Company ID does not exist")
  public void testCreateJobRecord_whenCompanyIdDoesNotExist_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer companyId = 1;
    Integer userId = 1;
    Integer positionId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class, () -> dao.createJobRecord(companyId, userId, positionId, startDate, endDate)
    );
  }

  @Test
  @DisplayName("Create job record: User ID is null")
  public void testCreateJobRecord_whenUserIdIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer companyId = 1;
    Integer positionId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class, () -> dao.createJobRecord(companyId, null, positionId, startDate, endDate)
    );
  }

  @Test
  @DisplayName("Create job record: User ID does not exist")
  public void testCreateJobRecord_whenUserIdDoesNotExist_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer companyId = 1;
    Integer userId = 1;
    Integer positionId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class, () -> dao.createJobRecord(companyId, userId, positionId, startDate, endDate)
    );
  }

  @Test
  @DisplayName("Create job record: Position ID is null")
  public void testCreateJobRecord_whenPositionIdIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer companyId = 1;
    Integer userId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class, () -> dao.createJobRecord(companyId, userId, null, startDate, endDate)
    );
  }

  @Test
  @DisplayName("Create job record: Position ID does not exist")
  public void testCreateJobRecord_whenPositionIdDoesNotExist_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer companyId = 1;
    Integer userId = 1;
    Integer positionId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class, () -> dao.createJobRecord(companyId, userId, positionId, startDate, endDate)
    );
  }

  @Test
  @DisplayName("Create job record: Start date is null")
  public void testCreateJobRecord_whenStartDateIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(JobListEntity.class));
    Integer companyId = 1;
    Integer userId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class, () -> dao.createJobRecord(companyId, userId, null, startDate, endDate)
    );
  }

  @Test
  @DisplayName("Create job record: Combination of the IDs already exists")
  public void testCreateJobRecord_whenCombinationOfIdsExists_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer companyId = 1;
    Integer userId = 1;
    Integer positionId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);

    assertThrows(
        PersistenceException.class, () -> dao.createJobRecord(companyId, userId, positionId, startDate, endDate)
    );
  }

  @Test
  @DisplayName("Create job record: End date is null")
  public void testCreateJobRecord_whenEndDateIsNull_thenThrowPersistenceException() {
    Integer companyId = 1;
    Integer userId = 1;
    Integer positionId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    JobListEntity expectedResult = new JobListEntity(new JobListId(companyId, positionId, userId), startDate);

    JobListEntity actualResult = dao.createJobRecord(companyId, userId, positionId, startDate, null);
    assertEquals(expectedResult, actualResult);
    verify(session).persist(any(JobListEntity.class));
    verify(transaction, times(1)).commit();
    verify(session, times(1)).close();
  }

  @Test
  @DisplayName("Create job record: End date is not null")
  public void testCreateJobRecord_whenEndDateIsNotNull_thenThrowPersistenceException() {
    Integer companyId = 1;
    Integer userId = 1;
    Integer positionId = 1;
    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 1);
    JobListEntity expectedResult = new JobListEntity(new JobListId(companyId, positionId, userId), startDate);
    expectedResult.setEndDate(endDate);

    JobListEntity actualResult = dao.createJobRecord(companyId, userId, positionId, startDate, endDate);
    assertEquals(expectedResult, actualResult);
    verify(session).persist(any(JobListEntity.class));
    verify(transaction, times(1)).commit();
    verify(session, times(1)).close();
  }

  @Test
  @DisplayName("Find job record: Company ID is null")
  public void testFindJobRecord_whenCompanyIdIsNull_thenReturnNull() {
    when(session.get(eq(JobListEntity.class), any(JobListId.class))).thenReturn(null);
    Integer userId = 1;
    Integer positionId = 1;

    JobListEntity actualResult = dao.findJobRecord(null, userId, positionId);
    assertNull(actualResult);
    verify(session).get(eq(JobListEntity.class), any(JobListId.class));
  }

}