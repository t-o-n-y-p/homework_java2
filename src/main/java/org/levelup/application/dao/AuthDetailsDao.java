package org.levelup.application.dao;

import org.levelup.hibernate.domain.UserEntity;

public interface AuthDetailsDao {

  UserEntity logIn(String login, String password);

}
