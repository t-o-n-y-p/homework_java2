package org.levelup.application.dao;

import org.levelup.application.domain.CompanyEntity;

import java.util.Collection;

public interface CompanyDao {

  CompanyEntity createCompany(String name, String ein, String address);
  CompanyEntity findById(Integer id);
  CompanyEntity findByEin(String ein);
  Collection<CompanyEntity> findByName(String name);
  CompanyEntity updateCompany(String ein, String name, String address);

}
