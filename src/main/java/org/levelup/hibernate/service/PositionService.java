package org.levelup.hibernate.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.levelup.hibernate.domain.Position;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class PositionService {

  private final SessionFactory factory;
  private final CriteriaBuilder criteriaBuilder;

  public PositionService(SessionFactory factory) {
    this.factory = factory;
    this.criteriaBuilder = factory.getCriteriaBuilder();
  }

  public Position createPosition(String name) {
    Position position;
    try (Session session = factory.openSession()) {
      position = new Position();
      position.setName(name);
      Transaction transaction = session.beginTransaction();
      session.persist(position);
      transaction.commit();
    }
    return position;
  }

  public Position findPositionById(Integer id) {
    Position position;
    try (Session session = factory.openSession()) {
      position = session.get(Position.class, id);
    }
    return position;
  }

  public Position findPositionByName(String name) {
    Position position;
    try (Session session = factory.openSession()) {
      CriteriaQuery<Position> criteriaQuery = criteriaBuilder.createQuery(Position.class);
      Root<Position> positionRoot = criteriaQuery.from(Position.class);
      Predicate predicate = criteriaBuilder.equal(positionRoot.get("name"), name);
      criteriaQuery
          .select(positionRoot)
          .where(predicate);
      position = session.createQuery(criteriaQuery).uniqueResult();
    }
    return position;
  }

  public Collection<Position> findAllPositions() {
    Collection<Position> positions;
    try (Session session = factory.openSession()) {
      CriteriaQuery<Position> criteriaQuery = criteriaBuilder.createQuery(Position.class);
      Root<Position> positionRoot = criteriaQuery.from(Position.class);
      criteriaQuery.select(positionRoot);
      positions = session.createQuery(criteriaQuery).getResultList();
    }
    return positions;
  }

  public Collection<Position> findAllPositionWhichNameLike(String nameMask) {
    Collection<Position> positions;
    try (Session session = factory.openSession()) {
      CriteriaQuery<Position> criteriaQuery = criteriaBuilder.createQuery(Position.class);
      Root<Position> positionRoot = criteriaQuery.from(Position.class);
      Predicate predicate = criteriaBuilder.like(positionRoot.get("name"), nameMask);
      criteriaQuery
          .select(positionRoot)
          .where(predicate);
      positions = session.createQuery(criteriaQuery).getResultList();
    }
    return positions;
  }

  public void deletePositionByName(String name) {
    deletePosition(findPositionByName(name));
  }

  public void deletePositionById(Integer id) {
    deletePosition(findPositionById(id));
  }

  private void deletePosition(Position position) {
    try (Session session = factory.openSession()) {
      Transaction transaction = session.beginTransaction();
      session.remove(position);
      transaction.commit();
    }
  }

}
