package org.levelup.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class JobSessionFactoryConfiguration {

  private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();

  public static SessionFactory getFactory() {
    return factory;
  }

  public static void closeFactory() {
    factory.close();
  }

}
