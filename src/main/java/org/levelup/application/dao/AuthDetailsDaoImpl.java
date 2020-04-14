package org.levelup.application.dao;

import org.hibernate.SessionFactory;
import org.levelup.application.domain.AuthDetailsEntity;
import org.levelup.application.domain.UserEntity;

import java.util.List;

public class AuthDetailsDaoImpl extends AbstractDao implements AuthDetailsDao {

  public AuthDetailsDaoImpl(SessionFactory factory) {
    super(factory);
  }

  @Override
  public UserEntity logIn(String login, String password) {
    List<AuthDetailsEntity> entities = runWithoutTransaction(
        s -> s.createQuery("from AuthDetailsEntity where login = :login and password = :password", AuthDetailsEntity.class)
            .setParameter("login", login)
            .setParameter("password", password)
            .getResultList()
    );
    return entities.isEmpty() ? null : entities.get(0).getUser();
  }
}
