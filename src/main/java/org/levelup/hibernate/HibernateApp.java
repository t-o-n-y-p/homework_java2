package org.levelup.hibernate;

import org.hibernate.SessionFactory;
import org.levelup.hibernate.domain.User;
import org.levelup.hibernate.service.UserService;

public class HibernateApp {

  public static void main(String[] args) {
    SessionFactory factory = new JobSessionFactoryConfiguration().configure();
    UserService userService = new UserService(factory);
//    User user = userService.createUserPersist("Oleg", "Olegov", "4543 546565");
//    System.out.println(user);
//    Integer id = userService.createUserSave("Dmitry", "Protsko", "4323 534234");
//    System.out.println(id);

//    Integer cloneId = userService.cloneUser(7, "4353 766765");
//    System.out.println(cloneId);

//    User user = userService.updateUserNameWithMerge(8, "Kolya");
//    System.out.println("merged user: " + Integer.toHexString(user.hashCode()));

//    User user = userService.mergeNewUser("Ivan", "Ivan", "5467 434654");
//    System.out.println(user);

    userService.updateUser("R", "r", "566");

    factory.close();
  }

}
