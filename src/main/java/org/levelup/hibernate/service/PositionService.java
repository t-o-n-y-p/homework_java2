package org.levelup.hibernate.service;

import org.levelup.application.domain.PositionEntity;

import java.util.List;

public class PositionService implements Dao<PositionEntity> {

  public PositionEntity createPosition(String name) {
    return runInTransactionWithSingleResult(s -> {
      PositionEntity positionEntity = new PositionEntity();
      positionEntity.setName(name);
      s.persist(positionEntity);
      return positionEntity;
    });
  }

  public PositionEntity findPositionById(Integer id) {
    return runWithSingleResult(s -> s.get(PositionEntity.class, id));
  }

  public PositionEntity findPositionByName(String name) {
    List<PositionEntity> positionEntities = run(
        s -> s.createQuery("from PositionEntity where name = :name", PositionEntity.class)
            .setParameter("name", name)
            .getResultList()
    );
    return positionEntities.isEmpty() ? null : positionEntities.get(0);
  }

  public List<PositionEntity> findAllPositions() {
    return run(
        s -> s.createQuery("from PositionEntity", PositionEntity.class)
            .getResultList()
    );
  }

  public List<PositionEntity> findAllPositionWhichNameLike(String nameMask) {
    return run(
        s -> s.createQuery("from PositionEntity where name like :nameMask", PositionEntity.class)
            .setParameter("nameMask", nameMask)
            .getResultList()
    );
  }

  public void deletePositionByName(String name) {
    delete(
        s -> s.createQuery("delete PositionEntity where name = :name")
            .setParameter("name", name)
            .executeUpdate()
    );
  }

  public void deletePositionById(Integer id) {
    delete(
        s -> s.createQuery("delete PositionEntity where id = :id")
            .setParameter("id", id)
            .executeUpdate()
    );
  }

}
