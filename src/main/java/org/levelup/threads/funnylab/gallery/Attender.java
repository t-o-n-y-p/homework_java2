package org.levelup.threads.funnylab.gallery;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;

@AllArgsConstructor
public class Attender implements Runnable {

  private final Gallery gallery;

  @Override
  @SneakyThrows
  public void run() {
    Random r = new Random();
    Thread.sleep(r.nextInt(5000) + 1);
    gallery.enter();
  }
}
