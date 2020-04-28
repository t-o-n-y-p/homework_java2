package org.levelup.threads.funnylab.cannibals;

import lombok.SneakyThrows;

public class Pot {

  private final int capacity = 12;
  private int currentState;
  private boolean chefWasNotified = true;

  @SneakyThrows
  public synchronized void eat() {
    System.out.println(Thread.currentThread().getName() + ": Found " + currentState + " pieces");
    if (currentState > 0) {
      currentState--;
      System.out.println(Thread.currentThread().getName() + ": Finished eating.");
    } else {
      if (!chefWasNotified) {
        chefWasNotified = true;
        System.out.println("Notified chef!");
        notify();
      }
      wait();
    }
  }

  @SneakyThrows
  public synchronized void cook() {
    while (currentState > 0) {
      wait();
    }
    System.out.println("Cooking...");
    Thread.sleep(5000);
    currentState = capacity;
    chefWasNotified = false;
    System.out.println("Meat is ready!");
    notifyAll();
  }

}
