package org.levelup.threads.funnylab.pooh;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;

@AllArgsConstructor
public class Bee implements Runnable {

  private final Jar jar;

  @Override
  @SneakyThrows
  public void run() {
    Random r = new Random();
    while (true) {
      Thread.sleep(r.nextInt(10000) + 1);
      jar.fill();
    }
  }
}
