package org.levelup.application.dao;

import org.levelup.application.domain.CompanyEntity;

public interface CompanyDao {

  CompanyEntity createCompany(String name, String ein, String address);
  CompanyEntity findById(Integer id);
  CompanyEntity findByEin(String ein);
  CompanyEntity findByName(String name);
  CompanyEntity updateCompany(String ein, String name, String address);
  CompanyEntity updateCompany(CompanyEntity company, String name, String address);

}
