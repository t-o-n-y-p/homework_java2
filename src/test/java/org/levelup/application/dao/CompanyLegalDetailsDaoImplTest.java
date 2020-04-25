package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.CompanyLegalDetailsEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CompanyLegalDetailsDaoImplTest {

  @Mock
  private SessionFactory factory;
  @Mock
  private Session session;
  @Mock
  private Transaction transaction;
  @Mock
  private Query<CompanyLegalDetailsEntity> query;

  @InjectMocks
  private CompanyLegalDetailsDaoImpl dao;

  @BeforeEach
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    when(factory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    when(session.createQuery(anyString(), eq(CompanyLegalDetailsEntity.class))).thenReturn(query);
    when(query.setParameter(eq("bankName"), anyString())).thenReturn(query);
    when(query.setParameter(eq("bankName"), eq(null))).thenReturn(query);
  }

  @AfterEach
  public void removeDao() {
    dao = null;
  }

  @Test
  @DisplayName("Update legal details: Bank name is null")
  public void testUpdateLegalDetailsInCompany_whenBankNameIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(CompanyLegalDetailsEntity.class));
    Integer id = 1;
    String bic = "company bic";

    assertThrows(PersistenceException.class, () -> dao.updateLegalDetailsInCompany(id, null, bic));

    verify(session).close();
  }

  @Test
  @DisplayName("Update legal details: Bank name is too long")
  public void testUpdateLegalDetailsInCompany_whenBankNameIsToLong_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer id = 1;
    String bankName = "company bank name company bank name company bank name company bank name company bank name " +
        "company bank name company bank name company bank name";
    String bic = "company bic";

    assertThrows(PersistenceException.class, () -> dao.updateLegalDetailsInCompany(id, bankName, bic));

    verify(session).persist(any(CompanyLegalDetailsEntity.class));
    verify(session).close();
  }

  @Test
  @DisplayName("Update legal details: Bic is null")
  public void testUpdateLegalDetailsInCompany_whenBicIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(CompanyLegalDetailsEntity.class));
    Integer id = 1;
    String bankName = "company bank name";

    assertThrows(PersistenceException.class, () -> dao.updateLegalDetailsInCompany(id, bankName, null));

    verify(session).close();
  }

  @Test
  @DisplayName("Update legal details: Bic is too long")
  public void testUpdateLegalDetailsInCompany_whenBicIsTooLong_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer id = 1;
    String bankName = "company bank name";
    String bic = "000000000000000000000000000000000000000000000000000000000000";

    assertThrows(PersistenceException.class, () -> dao.updateLegalDetailsInCompany(id, bankName, bic));

    verify(session).persist(any(CompanyLegalDetailsEntity.class));
    verify(session).close();
  }

  @Test
  @DisplayName("Update legal details: Company ID is null")
  public void testUpdateLegalDetailsInCompany_whenCompanyIdIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(CompanyLegalDetailsEntity.class));
    String bankName = "company bank name";
    String bic = "company bic";

    assertThrows(PersistenceException.class, () -> dao.updateLegalDetailsInCompany(null, bankName, bic));

    verify(session).close();
  }

  @Test
  @DisplayName("Update legal details: Company does not exist")
  public void testUpdateLegalDetailsInCompany_whenCompanyDoesNotExist_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(transaction).commit();
    Integer id = 1;
    String bankName = "company bank name";
    String bic = "company bic";

    assertThrows(PersistenceException.class, () -> dao.updateLegalDetailsInCompany(id, bankName, bic));

    verify(session).persist(any(CompanyLegalDetailsEntity.class));
    verify(session).close();
  }

  @Test
  @DisplayName("Update legal details: Company exists")
  public void testUpdateLegalDetailsInCompany_whenCompanyExists_thenPersistNewCompanyLegalDetails() {
    Integer id = 1;
    String bankName = "company bank name";
    String bic = "company bic";
    dao.updateLegalDetailsInCompany(id, bankName, bic);

    verify(session).persist(any(CompanyLegalDetailsEntity.class));
    verify(transaction, times(1)).commit();
    verify(session, times(1)).close();
  }

  @Test
  @DisplayName("Update legal details: Legal details already exist")
  public void testUpdateLegalDetailsInCompany_whenLegalDetailsExist_thenThrowPersistenceException() {
    Integer id = 1;
    String bankName = "company bank name";
    String bic = "company bic";
    dao.updateLegalDetailsInCompany(id, bankName, bic);
    doThrow(PersistenceException.class).when(transaction).commit();

    assertThrows(PersistenceException.class, () -> dao.updateLegalDetailsInCompany(id, bankName, bic));

    verify(session, times(2)).persist(any(CompanyLegalDetailsEntity.class));
    verify(session, times(2)).close();
  }

  @Test
  @DisplayName("Find by bank name: Bank name is null")
  public void testFindAllByBankName_whenBankNameIsNull_returnEmptyCollection() {
    List<CompanyLegalDetailsEntity> expectedResult = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResult);

    Collection<CompanyLegalDetailsEntity> actualResult = dao.findAllByBankName(null);
    assertTrue(actualResult.isEmpty());

    verifyFindByBankNameCalls(null);
  }

  @Test
  @DisplayName("Find by bank name: Bank name is too long")
  public void testFindAllByBankName_whenBankNameIsTooLong_returnEmptyCollection() {
    String bankName = "company bank name company bank name company bank name company bank name company bank name " +
        "company bank name company bank name company bank name company bank name company bank name";
    List<CompanyLegalDetailsEntity> expectedResult = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResult);

    Collection<CompanyLegalDetailsEntity> actualResult = dao.findAllByBankName(bankName);
    assertTrue(actualResult.isEmpty());

    verifyFindByBankNameCalls(bankName);
  }

  @Test
  @DisplayName("Find by bank name: Bank name does not exist")
  public void testFindAllByBankName_whenBankNameDoesNotExist_returnEmptyCollection() {
    String bankName = "company bank name";
    List<CompanyLegalDetailsEntity> expectedResult = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResult);

    Collection<CompanyLegalDetailsEntity> actualResult = dao.findAllByBankName(bankName);
    assertTrue(actualResult.isEmpty());

    verifyFindByBankNameCalls(bankName);
  }

  @Test
  @DisplayName("Find by bank name: Bank name exists")
  public void testFindAllByBankName_whenBankNameDoesNotExist_returnLegalDetailsCollection() {
    String bankName = "company bank name";
    List<CompanyLegalDetailsEntity> expectedResult = new ArrayList<>();
    expectedResult.add(new CompanyLegalDetailsEntity(1, bankName, "company bic"));
    when(query.getResultList()).thenReturn(expectedResult);

    Collection<CompanyLegalDetailsEntity> actualResult = dao.findAllByBankName(bankName);
    assertEquals(expectedResult, actualResult);

    verifyFindByBankNameCalls(bankName);
  }

  private void verifyFindByBankNameCalls(String bankName) {
    verify(session, times(1)).createQuery(
        eq("from CompanyLegalDetailsEntity where bankName = :bankName"), eq(CompanyLegalDetailsEntity.class)
    );
    verify(query, times(1)).setParameter(eq("bankName"), eq(bankName));
    verify(query, times(1)).getResultList();
    verify(session, times(1)).close();
  }

}