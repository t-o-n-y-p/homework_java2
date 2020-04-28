package org.levelup.threads.funnylab.supermarket;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Employee implements Runnable {

  private final Supermarket supermarket;
  private final int id;

  @Override
  public void run() {
    while (true) {
      supermarket.dequeue(id);
    }
  }
}
