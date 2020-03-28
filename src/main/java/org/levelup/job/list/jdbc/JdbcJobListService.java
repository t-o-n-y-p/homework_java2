package org.levelup.job.list.jdbc;

import org.levelup.job.list.domain.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcJobListService {

  public Position createPosition(String name) throws SQLException {
    Connection connection = JdbcUtils.getConnection();
    PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO positions (name) VALUES (?)"
    );
    statement.setString(1, name);
    int rowChanged = statement.executeUpdate();
    System.out.println("Количество добавленных строк: " + rowChanged);

    Statement selectStatement = connection.createStatement();
    ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM positions WHERE name = '" + name + "'");
    resultSet.next();
    int id = resultSet.getInt(1);
    String positionName = resultSet.getString("name");
    System.out.println("Должность: ID = " + id + ", name = " + positionName);

    return new Position(id, positionName);
  }

  public void deletePosition(String name) throws SQLException {
    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM positions WHERE name = ?");
      statement.setString(1, name);

      int rowDeleted = statement.executeUpdate();
      System.out.println("Удалено позиций: " + rowDeleted);
    }
  }

  public Collection<Position> findAll() throws SQLException {

    try (Connection connection = JdbcUtils.getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM positions");
      return extractPositions(resultSet);
    }

  }

  private Collection<Position> extractPositions(ResultSet resultSet) throws SQLException {

    Collection<Position> positions = new ArrayList<>();
    while (resultSet.next()) {
      positions.add(
          new Position(resultSet.getInt("id"), resultSet.getString("name"))
      );
    }
    return positions;

  }

  public Collection<Position> findPositionWithNameLike(String name) throws SQLException {

    try (Connection connection = JdbcUtils.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions WHERE name LIKE ?");
      statement.setString(1, name);
      return extractPositions(statement.executeQuery());
    }

  }

}
