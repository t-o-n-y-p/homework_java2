package org.levelup.threads.funnylab.gardeners2;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class Destroyer implements Runnable {

  private final Garden garden;

  @Override
  @SneakyThrows
  public void run() {
    while (true) {
      Thread.sleep(5000);
      garden.destroy();
    }
  }
}
