package org.levelup.application.dao;

import org.hibernate.SessionFactory;
import org.levelup.application.domain.CompanyEntity;

import java.util.Collection;
import java.util.List;

public class CompanyDaoImpl extends AbstractDao implements CompanyDao {

  public CompanyDaoImpl(SessionFactory factory) {
    super(factory);
  }

  @Override
  public CompanyEntity createCompany(String name, String ein, String address) {
    return runInTransaction(s -> {
      CompanyEntity entity = new CompanyEntity();
      entity.setName(name);
      entity.setEin(ein);
      entity.setAddress(address);
      s.persist(entity);
      return entity;
    });
  }

  @Override
  public CompanyEntity findById(Integer id) {
    return runWithoutTransaction(s -> s.get(CompanyEntity.class, id));
  }

  @Override
  public CompanyEntity findByEin(String ein) {
    List<CompanyEntity> entities = runWithoutTransaction(
        s -> s.createQuery("from CompanyEntity where ein = :ein", CompanyEntity.class)
            .setParameter("ein", ein)
            .getResultList()
    );
    return entities.isEmpty() ? null : entities.get(0);
  }

  @Override
  public Collection<CompanyEntity> findByName(String name) {
    return runWithoutTransaction(s -> s.createQuery("from CompanyEntity where name = :name", CompanyEntity.class)
          .setParameter("name", name)
          .getResultList()
    );
  }

  @Override
  public CompanyEntity updateCompany(String ein, String name, String address) {
    CompanyEntity company = findByEin(ein);
    return company == null ? null : runInTransaction(s -> {
      company.setName(name);
      company.setAddress(address);
      return (CompanyEntity) s.merge(company);
    });
  }

}
