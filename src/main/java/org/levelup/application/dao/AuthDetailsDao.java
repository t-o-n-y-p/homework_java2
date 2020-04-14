package org.levelup.application.dao;

import org.levelup.application.domain.UserEntity;

public interface AuthDetailsDao {

  UserEntity logIn(String login, String password);

}
