package org.levelup.threads.funnylab.supermarket;

import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor
public class Customer implements Runnable {

  private final Supermarket supermarket;

  @Override
  public void run() {
    System.out.println("Customer " + Thread.currentThread().getName() + " created");
    Random r = new Random();
    supermarket.enqueue(r.nextInt(2));
  }
}
