package org.levelup.hibernate;

import org.levelup.hibernate.domain.PositionEntity;
import org.levelup.hibernate.service.PositionService;
import org.levelup.hibernate.service.UserService;

public class HibernateApp {

  public static void main(String[] args) {
    System.out.println("UserService test");
    UserService userService = new UserService();
    System.out.println("User added:");
    System.out.println(userService.createUser("Jimmy", "White", "1234 567890"));
    System.out.println("User added once more:");
    System.out.println(userService.createUser("Jimmy", "White", "4321 098765"));
    System.out.println("Another user added:");
    System.out.println(userService.createUser("Michael", "White", "7890 123456"));
    System.out.println("Whites:");
    System.out.println(userService.findByLastName("White"));
    System.out.println("Jimmy Whites:");
    System.out.println(userService.findByNameAndLastName("Jimmy", "White"));
    System.out.println("Updated 4321 098765 passport owner");
    System.out.println(userService.updateUser("4321 098765", "Mark", "Williams"));
    System.out.println("Removing users");
    userService.deleteUserByPassport("1234 567890");
    userService.deleteUserByPassport("4321 098765");
    userService.deleteUserByPassport("7890 123456");
    System.out.println("Users were deleted.");

    System.out.println("PositionService test");
    PositionService positionService = new PositionService();
    System.out.println("Initial positions:");
    System.out.println(positionService.findAllPositions());
    System.out.println("Added positions:");
    PositionEntity designer = positionService.createPosition("Designer");
    System.out.println(positionService.findPositionById(designer.getId()));
    positionService.createPosition("System analyst");
    System.out.println(positionService.findPositionByName("System analyst"));
    System.out.println(positionService.createPosition("Business analyst"));
    System.out.println("All positions:");
    System.out.println(positionService.findAllPositions());
    System.out.println("Analysts:");
    System.out.println(positionService.findAllPositionWhichNameLike("%analyst"));
    positionService.deletePositionByName("System analyst");
    positionService.deletePositionByName("Business analyst");
    positionService.deletePositionById(designer.getId());
    System.out.println("Remaining positions:");
    System.out.println(positionService.findAllPositions());

    JobSessionFactoryConfiguration.closeFactory();
  }

}
