package org.levelup.threads.funnylab.pooh;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pooh implements Runnable {

  private final Jar jar;

  @Override
  public void run() {
    while (true) {
      jar.clear();
    }
  }
}
