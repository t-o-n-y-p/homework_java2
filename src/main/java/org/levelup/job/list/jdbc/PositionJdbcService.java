package org.levelup.job.list.jdbc;

import org.levelup.job.list.domain.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class PositionJdbcService implements PositionService {
  @Override
  public Position createPosition(String name) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO positions (name) VALUES (?)");
      statement.setString(1, name);
      statement.executeUpdate();
    }
    return findPositionByName(name);
  }

  @Override
  public void deletePositionById(int id) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM positions WHERE id = ?");
      statement.setInt(1, id);
      statement.executeUpdate();
    }
  }

  @Override
  public void deletePositionByName(String name) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM positions WHERE name = ?");
      statement.setString(1, name);
      statement.executeUpdate();
    }
  }

  @Override
  public Collection<Position> findAllPositionWhichNameLike(String nameMask) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions WHERE name LIKE ?");
      statement.setString(1, nameMask);
      return extractPositions(statement.executeQuery());
    }
  }

  @Override
  public Position findPositionById(int id) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions WHERE id = ?");
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return null;
      }
      return new Position(
          resultSet.getInt("id"),
          resultSet.getString("name")
      );
    }
  }

  @Override
  public Collection<Position> findAllPositions() throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      return extractPositions(
          connection.prepareStatement("SELECT * FROM positions").executeQuery()
      );
    }
  }

  @Override
  public Position findPositionByName(String name) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions WHERE name = ?");
      statement.setString(1, name);
      ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return null;
      }
      return new Position(
          resultSet.getInt("id"),
          resultSet.getString("name")
      );
    }
  }

  private Collection<Position> extractPositions(ResultSet resultSet) throws SQLException {
    Collection<Position> positions = new ArrayList<>();
    while (resultSet.next()) {
      positions.add(
          new Position(
              resultSet.getInt("id"),
              resultSet.getString("name")
          )
      );
    }
    return positions;
  }
}
