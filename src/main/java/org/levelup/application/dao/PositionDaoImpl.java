package org.levelup.application.dao;

import org.hibernate.SessionFactory;
import org.levelup.application.domain.PositionEntity;

import java.util.List;

public class PositionDaoImpl extends AbstractDao implements PositionDao {

  public PositionDaoImpl(SessionFactory factory) {
    super(factory);
  }

  @Override
  public PositionEntity createPosition(String name) {
    return runInTransaction(s -> {
      PositionEntity position = new PositionEntity();
      position.setName(name);
      s.persist(position);
      return position;
    });
  }

  @Override
  public PositionEntity findByName(String name) {
    List<PositionEntity> entities = runWithoutTransaction(
        s -> s.createQuery("from PositionEntity where name = :name", PositionEntity.class)
            .setParameter("name", name)
            .getResultList()
    );
    return entities.isEmpty() ? null : entities.get(0);
  }
}
