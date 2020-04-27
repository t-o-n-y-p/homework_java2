package org.levelup.threads.funnylab.pooh;

import lombok.SneakyThrows;

public class Jar {

  private final int capacity = 9;
  private int currentState;

  public synchronized void fill() {
    currentState++;
    System.out.println("Amount of honey: " + currentState);
    if (currentState == capacity) {
      System.out.println("Pooh notified!");
      notify();
    }
  }

  @SneakyThrows
  public synchronized void clear() {
    while (currentState < capacity) {
      System.out.println("Pooh is sleeping...");
      wait();
    }
    System.out.println("Pooh is eating the honey!");
    currentState = 0;
    System.out.println("Amount of honey: " + currentState);
  }

}
