package org.levelup.threads.funnylab.pooh2;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pooh implements Runnable {

  private final Hive hive;

  @Override
  public void run() {
    while (true) {
      hive.eatHoney();
    }
  }
}
