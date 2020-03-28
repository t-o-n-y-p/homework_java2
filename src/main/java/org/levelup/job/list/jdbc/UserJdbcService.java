package org.levelup.job.list.jdbc;

import org.levelup.job.list.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class UserJdbcService implements UserService {
  @Override
  public User createUser(String name, String lastName, String passport) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO users (name, last_name, passport) VALUES (?, ?, ?)"
      );
      statement.setString(1, name);
      statement.setString(2, lastName);
      statement.setString(3, passport);
      statement.executeUpdate();
    }
    return findByPassport(passport);
  }

  @Override
  public User findByPassport(String passport) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE passport = ?");
      statement.setString(1, passport);
      ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return null;
      }
      return new User(
          resultSet.getInt("id"),
          resultSet.getString("name"),
          resultSet.getString("last_name"),
          resultSet.getString("passport")
      );
    }
  }

  @Override
  public Collection<User> findByNameAndLastName(String name, String lastName) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM users WHERE name = ? AND last_name = ?"
      );
      statement.setString(1, name);
      statement.setString(2, lastName);
      return extractUsers(statement.executeQuery());
    }
  }

  @Override
  public Collection<User> findByLastName(String lastName) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM users WHERE last_name = ?"
      );
      statement.setString(1, lastName);
      return extractUsers(statement.executeQuery());
    }
  }

  @Override
  public void deleteUserByPassport(String passport) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE passport = ?");
      statement.setString(1, passport);
      statement.executeUpdate();
    }
  }

  @Override
  public User updateUser(String passport, String newName, String newLastName) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(
          "UPDATE users SET name = ?, last_name = ? WHERE passport = ?"
      );
      statement.setString(1, newName);
      statement.setString(2, newLastName);
      statement.setString(3, passport);
      statement.executeUpdate();
    }
    return findByPassport(passport);
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
