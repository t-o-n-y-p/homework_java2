package org.levelup.threads.funnylab.pooh;

import lombok.SneakyThrows;

public class Jar {

  private final int capacity = 9;
  private int currentState;

  private final Object pooh = new Object();
  private final Object bees = new Object();

  @SneakyThrows
  public void fill() {
    synchronized (bees) {
      while (currentState == capacity) {
        bees.wait();
      }
    }
    synchronized (this) {
      currentState++;
      System.out.println("Amount of honey: " + currentState);
    }
    if (currentState == capacity) {
      System.out.println("Pooh notified!");
      synchronized (pooh) {
        pooh.notify();
      }
    }
  }

  @SneakyThrows
  public void clear() {
    synchronized (pooh) {
      while (currentState < capacity) {
        System.out.println("Pooh is sleeping...");
        pooh.wait();
      }
    }
    synchronized (this) {
      System.out.println("Pooh is eating the honey...");
      Thread.sleep(1000);
      currentState = 0;
      System.out.println("Amount of honey: " + currentState);
    }
    synchronized (bees) {
      bees.notifyAll();
    }
  }

}
