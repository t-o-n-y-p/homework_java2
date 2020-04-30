package org.levelup.threads.funnylab.supermarket;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SupermarketApp {

  @SneakyThrows
  public static void main(String[] args) {

    int numberOfEmployees = 2;
    Supermarket supermarket = new Supermarket(numberOfEmployees);
    for (int i = 0; i < numberOfEmployees; i++) {
      new Thread(new Employee(supermarket, i)).start();
    }

    TimeUnit.SECONDS.sleep(1);
    Random r = new Random();
    for (int i = 0; i < 30; i++) {
      TimeUnit.SECONDS.sleep(r.nextInt(10) + 1);
      Customer customer = new Customer(supermarket);
      new Thread(customer).start();
    }

  }

}
