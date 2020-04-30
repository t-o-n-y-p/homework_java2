package org.levelup.threads.funnylab.pooh2;

import lombok.SneakyThrows;

public class Hive {

  private int beesInsideTheHive;
  private final int capacity = 30;
  private int currentState;

  private final Object bees = new Object();
  private final Object pooh = new Object();

  public Hive(int bees) {
    this.beesInsideTheHive = bees;
  }

  @SneakyThrows
  public void leave() {
    synchronized (bees) {
      while (beesInsideTheHive < 2) {
        bees.wait();
      }
      beesInsideTheHive--;
      System.out.println("Bee has left. Bees remaining: " + beesInsideTheHive);
    }
  }

  @SneakyThrows
  public synchronized void bringHoney() {
    synchronized (bees) {
      beesInsideTheHive++;
      System.out.println("Bee has arrived. Bees remaining: " + beesInsideTheHive);
      if (currentState < capacity) {
        currentState++;
      }
      System.out.println("Amount of honey: " + currentState);
      bees.notifyAll();
    }
    synchronized (pooh) {
      pooh.notify();
    }
  }

  @SneakyThrows
  public void eatHoney() {
    synchronized (pooh) {
      while (currentState < capacity / 2) {
        pooh.wait();
      }
    }
    if (beesInsideTheHive < 3) {
      synchronized (pooh) {
        currentState = 0;
      }
      System.out.println("Pooh: Ha-ha-ha! Eating honey...");
      Thread.sleep(1000);
      System.out.println("Pooh: Honey is gone!");
    } else {
      System.out.println("Pooh: Ouch!");
      Thread.sleep(2000);
    }
  }

}
