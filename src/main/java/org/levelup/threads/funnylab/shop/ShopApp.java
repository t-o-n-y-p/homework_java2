package org.levelup.threads.funnylab.shop;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ShopApp {

  @SneakyThrows
  public static void main(String[] args) {

    Shop shop = new Shop();
    for (int i = 0; i < 3; i++) {
      new Thread(new Employee(shop, i)).start();
    }

    TimeUnit.SECONDS.sleep(1);
    Random r = new Random();
    for (int i = 0; i < 30; i++) {
      TimeUnit.SECONDS.sleep(r.nextInt(10) + 1);
      Customer customer = new Customer(shop);
      new Thread(customer).start();
    }

  }

}
