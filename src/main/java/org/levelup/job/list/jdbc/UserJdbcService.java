package org.levelup.job.list.jdbc;

import org.levelup.job.list.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class UserJdbcService implements UserService {
  @Override
  public User createUser(String name, String lastName, String passport) throws SQLException {
    return null;
  }

  @Override
  public User findByPassport(String passport) throws SQLException {
    return null;
  }

  @Override
  public Collection<User> findByNameAndLastName(String name, String lastName) throws SQLException {
    return null;
  }

  @Override
  public Collection<User> findByLastName(String lastName) throws SQLException {
    return null;
  }

  @Override
  public void deleteUserByPassport(String passport) throws SQLException {

  }

  @Override
  public User updateUser(String name, String lastName, String passport) throws SQLException {
    return null;
  }

  private Collection<User> extractUsers(ResultSet resultSet) throws SQLException {
    Collection<User> users = new ArrayList<>();
    while (resultSet.next()) {
      users.add(
          new User(
              resultSet.getInt("id"),
              resultSet.getString("name"),
              resultSet.getString("last_name"),
              resultSet.getString("passport")
          )
      );
    }
    return users;
  }
}
