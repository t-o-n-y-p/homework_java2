package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.CompanyEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CompanyDaoImplTest {

  @Mock
  private SessionFactory factory;
  @Mock
  private Session session;
  @Mock
  private Transaction transaction;
  @Mock
  private Query<CompanyEntity> query;

  @InjectMocks
  private CompanyDaoImpl dao;

  @BeforeEach
  public void initialize() {
    MockitoAnnotations.initMocks(this);
    when(factory.openSession()).thenReturn(session);
    when(session.beginTransaction()).thenReturn(transaction);
    when(session.createQuery(anyString(), eq(CompanyEntity.class))).thenReturn(query);
    when(query.setParameter(eq("name"), anyString())).thenReturn(query);
    when(query.setParameter(eq("name"), eq(null))).thenReturn(query);
    when(query.setParameter(eq("ein"), anyString())).thenReturn(query);
    when(query.setParameter(eq("ein"), eq(null))).thenReturn(query);
  }

  @AfterEach
  public void removeDao() {
    dao = null;
  }

  @Test
  @DisplayName("Create company: Name is null")
  public void testCreateCompany_whenCompanyNameIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(CompanyEntity.class));
    String ein = "company ein";
    String address = "company address";
    assertThrows(PersistenceException.class, () -> dao.createCompany(null, ein, address));
  }

  @Test
  @DisplayName("Create company: Name is too long")
  public void testCreateCompany_whenCompanyNameIsTooLong_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(CompanyEntity.class));
    String name = "company name company name company name company name company name company name company name " +
        "company name company name company name";
    String ein = "company ein";
    String address = "company address";
    assertThrows(PersistenceException.class, () -> dao.createCompany(name, ein, address));
  }

  @Test
  @DisplayName("Create company: Address is null")
  public void testCreateCompany_whenCompanyAddressIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(CompanyEntity.class));
    String ein = "company ein";
    String name = "company name";
    assertThrows(PersistenceException.class, () -> dao.createCompany(name, ein, null));
  }

  @Test
  @DisplayName("Create company: Ein is null")
  public void testCreateCompany_whenCompanyEinIsNull_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(CompanyEntity.class));
    String name = "company name";
    String address = "company address";
    assertThrows(PersistenceException.class, () -> dao.createCompany(name, null, address));
  }

  @Test
  @DisplayName("Create company: Ein is too long")
  public void testCreateCompany_whenCompanyEinIsTooLong_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(CompanyEntity.class));
    String name = "company name";
    String ein = "company ein company ein company ein company ein company ein company ein company ein company ein";
    String address = "company address";
    assertThrows(PersistenceException.class, () -> dao.createCompany(name, ein, address));
  }

  @Test
  @DisplayName("Create company: Ein already exists")
  public void testCreateCompany_whenCompanyEinAlreadyExists_thenThrowPersistenceException() {
    doThrow(PersistenceException.class).when(session).persist(any(CompanyEntity.class));
    String name = "company name";
    String ein = "company ein";
    String address = "company address";
    assertThrows(PersistenceException.class, () -> dao.createCompany(name, ein, address));
  }

  @Test
  @DisplayName("Create company: Ein is not added")
  public void testCreateCompany_whenEinIsNotAdded_thenPersistNewCompany() {
    String name = "company name";
    String ein = "company ein";
    String address = "company address";
    CompanyEntity actualResult = dao.createCompany(name, ein, address);
    assertEquals(name, actualResult.getName());
    assertEquals(ein, actualResult.getEin());
    assertEquals(address, actualResult.getAddress());
    verify(session).persist(any(CompanyEntity.class));
    verify(transaction, times(1)).commit();
    verify(session, times(1)).close();
  }

  @Test
  @DisplayName("Find by ID: ID is null")
  public void testFindById_whenIdIsNull_thenThrowNullPointerException() {
    doThrow(NullPointerException.class).when(session).get(eq(CompanyEntity.class), eq(null));
    assertThrows(NullPointerException.class, () -> dao.findById(null));
  }

  @Test
  @DisplayName("Find by ID: Company does not exist")
  public void testFindById_whenIdDoesNotExist_thenReturnNull() {
    Integer id = 1;
    when(session.get(eq(CompanyEntity.class), any(Integer.class))).thenReturn(null);
    CompanyEntity actualResult = dao.findById(id);
    assertNull(actualResult);

    verifyFindByIdCalls(id);
  }

  @Test
  @DisplayName("Find by ID: Company exists")
  public void testFindById_whenIdExists_thenReturnCompany() {
    Integer id = 1;
    CompanyEntity expectedResult = new CompanyEntity();
    expectedResult.setId(id);
    when(session.get(eq(CompanyEntity.class), eq(id))).thenReturn(expectedResult);
    CompanyEntity actualResult = dao.findById(id);
    assertEquals(expectedResult, actualResult);

    verifyFindByIdCalls(id);
  }

  @Test
  @DisplayName("Find by ein: Ein is null")
  public void testFindByEin_whenEinIsNull_thenReturnNull() {
    List<CompanyEntity> expectedResultList = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResultList);

    CompanyEntity actualResult = dao.findByEin(null);
    assertNull(actualResult);
    verifyFindByEinCalls(null);
  }

  @Test
  @DisplayName("Find by ein: Ein does not exist")
  public void testFindByEin_whenEinDoesNotExist_thenReturnNull() {
    String ein = "company ein";
    List<CompanyEntity> expectedResultList = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResultList);

    CompanyEntity actualResult = dao.findByEin(ein);
    assertNull(actualResult);
    verifyFindByEinCalls(ein);
  }

  @Test
  @DisplayName("Find by ein: Ein is too long")
  public void testFindByEin_whenEinIsTooLong_thenReturnNull() {
    String ein = "company ein company ein company ein company ein company ein company ein company ein company ein";
    List<CompanyEntity> expectedResultList = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResultList);

    CompanyEntity actualResult = dao.findByEin(ein);
    assertNull(actualResult);
    verifyFindByEinCalls(ein);
  }

  @Test
  @DisplayName("Find by ein: Ein exists")
  public void testFindByEin_whenEinExists_thenReturnCompany() {
    String ein = "company ein";
    List<CompanyEntity> expectedResultList = new ArrayList<>();
    CompanyEntity expectedResult = new CompanyEntity();
    expectedResult.setEin(ein);
    expectedResultList.add(expectedResult);
    when(query.getResultList()).thenReturn(expectedResultList);

    CompanyEntity actualResult = dao.findByEin(ein);
    assertEquals(expectedResult, actualResult);
    verifyFindByEinCalls(ein);
  }

  @Test
  @DisplayName("Find by name: Name is null")
  public void testFindByName_whenNameIsNull_thenReturnNull() {
    List<CompanyEntity> expectedResult = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResult);

    Collection<CompanyEntity> actualResult = dao.findByName(null);
    assertEquals(expectedResult, actualResult);
    verifyFindByNameCalls(null);
  }

  @Test
  @DisplayName("Find by name: Name does not exist")
  public void testFindByName_whenNameDoesNotExist_thenReturnNull() {
    String name = "company name";
    List<CompanyEntity> expectedResult = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResult);

    Collection<CompanyEntity> actualResult = dao.findByName(name);
    assertEquals(expectedResult, actualResult);
    verifyFindByNameCalls(name);
  }

  @Test
  @DisplayName("Find by name: Name is too long")
  public void testFindByName_whenNameIsTooLong_thenReturnNull() {
    String name = "company name company name company name company name company name company name " +
        "company name company name company name";
    List<CompanyEntity> expectedResult = new ArrayList<>();
    when(query.getResultList()).thenReturn(expectedResult);

    Collection<CompanyEntity> actualResult = dao.findByName(name);
    assertEquals(expectedResult, actualResult);
    verifyFindByNameCalls(name);
  }

  @Test
  @DisplayName("Find by name: Name exists")
  public void testFindByName_whenNameExists_thenReturnCompany() {
    String name = "company name";
    List<CompanyEntity> expectedResult = new ArrayList<>();
    CompanyEntity company = new CompanyEntity();
    company.setName(name);
    expectedResult.add(company);
    when(query.getResultList()).thenReturn(expectedResult);

    Collection<CompanyEntity> actualResult = dao.findByName(name);
    assertEquals(expectedResult, actualResult);
    verifyFindByNameCalls(name);
  }

  @Test
  @DisplayName("Update company by ein: Ein is null")
  public void testUpdateCompany_whenEinIsNull_thenReturnNull() {
    String name = "company name";
    String address = "company address";
    CompanyEntity actualResult = dao.updateCompany(null, name, address);
    assertNull(actualResult);

    verifyFindByEinCalls(null);
  }

  @Test
  @DisplayName("Update company by ein: Ein does not exist")
  public void testUpdateCompany_whenEinDoesNotExist_thenReturnNull() {
    String ein = "company ein";
    String name = "company name";
    String address = "company address";
    CompanyEntity actualResult = dao.updateCompany(ein, name, address);
    assertNull(actualResult);

    verifyFindByEinCalls(ein);
  }

  @Test
  @DisplayName("Update company by ein: Ein is too long")
  public void testUpdateCompany_whenEinIsTooLong_thenReturnNull() {
    String ein = "company ein company ein company ein company ein company ein company ein company ein";
    String name = "company name";
    String address = "company address";
    CompanyEntity actualResult = dao.updateCompany(ein, name, address);
    assertNull(actualResult);

    verifyFindByEinCalls(ein);
  }

  @Test
  @DisplayName("Update company by ein: Ein exists")
  public void testUpdateCompany_whenEinExists_thenMergeCompany() {
    String ein = "company ein";
    String name = "company name";
    String address = "company address";
    CompanyEntity expectedResult = new CompanyEntity();
    expectedResult.setEin(ein);
    expectedResult.setName(name);
    expectedResult.setAddress(address);
    when(session.merge(any(CompanyEntity.class))).thenReturn(expectedResult);
    List<CompanyEntity> expectedResultList = new ArrayList<>();
    expectedResultList.add(expectedResult);
    when(query.getResultList()).thenReturn(expectedResultList);

    CompanyEntity actualResult = dao.updateCompany(ein, name, address);
    assertEquals(expectedResult, actualResult);

    verifyUpdateCompanyCalls(ein, name, address);
  }

  @Test
  @DisplayName("Update company by ein: Name is null")
  public void testUpdateCompany_whenNameIsNull_thenThrowPersistenceException() {
    String ein = "company ein";
    String address = "company address";
    CompanyEntity expectedResult = new CompanyEntity();
    expectedResult.setEin(ein);
    expectedResult.setAddress(address);
    when(session.merge(any(CompanyEntity.class))).thenReturn(expectedResult);
    List<CompanyEntity> expectedResultList = new ArrayList<>();
    expectedResultList.add(expectedResult);
    when(query.getResultList()).thenReturn(expectedResultList);

    doThrow(PersistenceException.class).when(transaction).commit();

    assertThrows(PersistenceException.class, () -> dao.updateCompany(ein, null, address));

    verifyUpdateCompanyCalls(ein, null, address);
  }

  @Test
  @DisplayName("Update company by ein: Name is too long")
  public void testUpdateCompany_whenNameIsTooLong_thenThrowPersistenceException() {
    String ein = "company ein";
    String name = "company name company name company name company name company name company name " +
        "company name company name company name company name company name";
    String address = "company address";
    CompanyEntity expectedResult = new CompanyEntity();
    expectedResult.setEin(ein);
    expectedResult.setName(name);
    expectedResult.setAddress(address);
    when(session.merge(any(CompanyEntity.class))).thenReturn(expectedResult);
    List<CompanyEntity> expectedResultList = new ArrayList<>();
    expectedResultList.add(expectedResult);
    when(query.getResultList()).thenReturn(expectedResultList);

    doThrow(PersistenceException.class).when(transaction).commit();

    assertThrows(PersistenceException.class, () -> dao.updateCompany(ein, name, address));

    verifyUpdateCompanyCalls(ein, name, address);
  }

  @Test
  @DisplayName("Update company by ein: Address is null")
  public void testUpdateCompany_whenAddressIsNull_thenThrowPersistenceException() {
    String ein = "company ein";
    String name = "company name";
    CompanyEntity expectedResult = new CompanyEntity();
    expectedResult.setEin(ein);
    expectedResult.setName(name);
    when(session.merge(any(CompanyEntity.class))).thenReturn(expectedResult);
    List<CompanyEntity> expectedResultList = new ArrayList<>();
    expectedResultList.add(expectedResult);
    when(query.getResultList()).thenReturn(expectedResultList);

    doThrow(PersistenceException.class).when(transaction).commit();

    assertThrows(PersistenceException.class, () -> dao.updateCompany(ein, name, null));

    verifyUpdateCompanyCalls(ein, name, null);
  }

  private void verifyFindByIdCalls(Integer id) {
    verify(session, times(1)).get(eq(CompanyEntity.class), eq(id));
    verify(session, times(1)).close();
  }

  private void verifyFindByNameCalls(String name) {
    verify(session, times(1))
        .createQuery(eq("from CompanyEntity where name = :name"), eq(CompanyEntity.class));
    verify(query, times(1)).setParameter(eq("name"), eq(name));
    verify(query, times(1)).getResultList();
    verify(session, times(1)).close();
  }

  private void verifyFindByEinCalls(String ein) {
    verify(session, times(1))
        .createQuery(eq("from CompanyEntity where ein = :ein"), eq(CompanyEntity.class));
    verify(query, times(1)).setParameter(eq("ein"), eq(ein));
    verify(query, times(1)).getResultList();
    verify(session, times(1)).close();
  }

  private void verifyUpdateCompanyCalls(String ein, String name, String address) {
    verify(session, times(1))
        .createQuery(eq("from CompanyEntity where ein = :ein"), eq(CompanyEntity.class));
    verify(query, times(1)).setParameter(eq("ein"), eq(ein));
    verify(query, times(1)).getResultList();
    verify(session, times(1)).merge(any(CompanyEntity.class));
    verify(transaction, times(1)).commit();
    if (name != null && address != null && name.length() <= 100) {
      verify(session, times(2)).close();
    }
  }

}