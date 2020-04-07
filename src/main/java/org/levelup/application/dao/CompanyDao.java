package org.levelup.application.dao;

import org.levelup.application.domain.CompanyEntity;

public interface CompanyDao {

  void create(String name, String ein, String address);
  void findById(Integer id);
  CompanyEntity findByEin(String ein);

}
