package org.levelup.job.list;

import org.levelup.job.list.domain.Position;
import org.levelup.job.list.jdbc.PositionJdbcService;
import org.levelup.job.list.jdbc.UserJdbcService;

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

    System.out.println("\nUserJdbcService test");
    UserJdbcService userJdbcService = new UserJdbcService();
    System.out.println("User added:");
    System.out.println(userJdbcService.createUser("Jimmy", "White", "1234 567890"));
    System.out.println("User added once more:");
    System.out.println(userJdbcService.createUser("Jimmy", "White", "4321 098765"));
    System.out.println("Another user added:");
    System.out.println(userJdbcService.createUser("Michael", "White", "7890 123456"));
    System.out.println("Whites:");
    System.out.println(userJdbcService.findByLastName("White"));
    System.out.println("Jimmy Whites:");
    System.out.println(userJdbcService.findByNameAndLastName("Jimmy", "White"));
    System.out.println("Updated 4321 098765 passport owner");
    System.out.println(userJdbcService.updateUser("4321 098765", "Mark", "Williams"));
    System.out.println("Removing users");
    userJdbcService.deleteUserByPassport("1234 567890");
    userJdbcService.deleteUserByPassport("4321 098765");
    userJdbcService.deleteUserByPassport("7890 123456");
    System.out.println("Users were deleted.");
  }
}
