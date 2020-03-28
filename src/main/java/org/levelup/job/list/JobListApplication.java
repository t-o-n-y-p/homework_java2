package org.levelup.job.list;

import org.levelup.job.list.domain.Position;
import org.levelup.job.list.jdbc.PositionJdbcService;

import java.sql.SQLException;

public class JobListApplication {

  public static void main(String[] args) throws SQLException {
    System.out.println("PositionJdbcService test");
    PositionJdbcService positionJdbcService = new PositionJdbcService();
    System.out.println("Initial positions:");
    System.out.println(positionJdbcService.findAllPositions());
    System.out.println("Added positions:");
    Position designer = positionJdbcService.createPosition("Designer");
    System.out.println(positionJdbcService.findPositionById(designer.getId()));
    positionJdbcService.createPosition("System analyst");
    System.out.println(positionJdbcService.findPositionByName("System analyst"));
    System.out.println(positionJdbcService.createPosition("Business analyst"));
    System.out.println("All positions:");
    System.out.println(positionJdbcService.findAllPositions());
    System.out.println("Analysts:");
    System.out.println(positionJdbcService.findAllPositionWhichNameLike("%analyst"));
    positionJdbcService.deletePositionByName("System analyst");
    positionJdbcService.deletePositionByName("Business analyst");
    positionJdbcService.deletePositionById(designer.getId());
    System.out.println("Remaining positions:");
    System.out.println(positionJdbcService.findAllPositions());
  }

}
