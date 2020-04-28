package org.levelup.threads.funnylab.pooh2;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;

@AllArgsConstructor
public class Bee implements Runnable {

  private final Hive hive;

  @Override
  @SneakyThrows
  public void run() {
    Random r = new Random();
    while (true) {
      Thread.sleep(r.nextInt(1000) + 1);
      hive.leave();
      Thread.sleep(r.nextInt(1600) + 1);
      hive.bringHoney();
    }
  }
}
