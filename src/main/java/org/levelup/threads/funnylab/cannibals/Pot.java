package org.levelup.threads.funnylab.cannibals;

import lombok.SneakyThrows;

public class Pot {

  private final int capacity = 12;
  private int currentState;
  private boolean chefWasNotNotified;

  private final Object cook = new Object();
  private final Object cannibals = new Object();

  @SneakyThrows
  public void eat() {
    synchronized (cannibals) {
      System.out.println(Thread.currentThread().getName() + ": Found " + currentState + " pieces");
      if (currentState == 0 && chefWasNotNotified) {
        synchronized (this) {
          chefWasNotNotified = false;
        }
        System.out.println(Thread.currentThread().getName() + ": Notified chef!");
        synchronized (cook) {
          cook.notify();
        }
      }
      while (currentState == 0) {
        System.out.println(Thread.currentThread().getName() + ": Waiting for chef...");
        cannibals.wait();
      }
    }
    synchronized (this) {
      currentState--;
      System.out.println(Thread.currentThread().getName() + ": Finished eating.");
    }
  }

  @SneakyThrows
  public void cook() {
    synchronized (cook) {
      while (currentState > 0) {
        cook.wait();
      }
    }
    System.out.println("Cooking...");
    Thread.sleep(5000);
    synchronized (this) {
      currentState = capacity;
      chefWasNotNotified = true;
    }
    System.out.println("Meat is ready!");
    synchronized (cannibals) {
      cannibals.notifyAll();
    }
  }

}
