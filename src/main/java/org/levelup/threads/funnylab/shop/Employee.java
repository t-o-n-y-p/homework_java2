package org.levelup.threads.funnylab.shop;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Employee implements Runnable {

  private final Shop shop;
  private final int id;

  @Override
  public void run() {
    while (true) {
      shop.dequeue(id);
    }
  }
}