package org.levelup.hibernate;

import org.hibernate.SessionFactory;
import org.levelup.hibernate.domain.User;
import org.levelup.hibernate.service.UserService;

public class HibernateApp {

  public static void main(String[] args) {
    SessionFactory factory = new JobSessionFactoryConfiguration().configure();
    UserService userService = new UserService(factory);
    System.out.println(userService.updateUser("5467 434654", "John", "Smith"));

    factory.close();
  }

}
