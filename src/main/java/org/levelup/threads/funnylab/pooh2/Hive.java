package org.levelup.threads.funnylab.pooh2;

import lombok.SneakyThrows;

public class Hive {

  private int bees;
  private final int capacity = 30;
  private int currentState;

  public Hive(int bees) {
    this.bees = bees;
  }

  @SneakyThrows
  public synchronized void leave() {
    while (bees < 2) {
      wait();
    }
    bees--;
    System.out.println("Bee has left. Bees remaining: " + bees);
  }

  @SneakyThrows
  public synchronized void bringHoney() {
    bees++;
    System.out.println("Bee has arrived. Bees remaining: " + bees);
    if (currentState < capacity) {
      currentState++;
    }
    System.out.println("Amount of honey: " + currentState);
    notifyAll();
  }

  @SneakyThrows
  public void eatHoney() {
    synchronized (this) {
      while (currentState < capacity / 2) {
        wait();
      }
    }
    if (bees < 3) {
      synchronized (this) {
        currentState = 0;
      }
      System.out.println("Pooh: Ha-ha-ha! Eating honey...");
      Thread.sleep(2000);
      System.out.println("Finished.");
    } else {
      System.out.println("Pooh: Ouch!");
      Thread.sleep(2000);
    }
  }

}
