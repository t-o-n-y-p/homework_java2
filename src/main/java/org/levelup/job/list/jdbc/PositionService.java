package org.levelup.job.list.jdbc;

import org.levelup.job.list.domain.Position;

import java.sql.SQLException;
import java.util.Collection;

public interface PositionService {

  Position createPosition(String name) throws SQLException;
  void deletePositionById(int id) throws SQLException;
  void deletePositionByName(String name) throws SQLException;
  Collection<Position> findAllPositionWhichNameLike(String nameMask) throws SQLException;
  Position findPositionById(int id) throws SQLException;
  Collection<Position> findAllPositions() throws SQLException;
  Position findPositionByName(String name) throws SQLException;

}
