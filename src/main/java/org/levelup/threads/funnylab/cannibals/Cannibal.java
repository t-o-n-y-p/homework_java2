package org.levelup.threads.funnylab.cannibals;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;

@AllArgsConstructor
public class Cannibal implements Runnable {

  private final Pot pot;

  @Override
  @SneakyThrows
  public void run() {
    Random r = new Random();
    while (true) {
      Thread.sleep(r.nextInt(10000) + 1);
      pot.eat();
    }
  }
}
