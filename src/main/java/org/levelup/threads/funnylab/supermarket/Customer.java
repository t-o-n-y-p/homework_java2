package org.levelup.threads.funnylab.supermarket;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Customer implements Runnable {

  private final Supermarket supermarket;

  @Override
  public void run() {
    System.out.println("Customer " + Thread.currentThread().getName() + " created");
    supermarket.enqueue();
  }
}
