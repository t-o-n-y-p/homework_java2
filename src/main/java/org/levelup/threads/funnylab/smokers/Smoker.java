package org.levelup.threads.funnylab.smokers;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Smoker implements Runnable {

  private final Table table;
  private final String resource;

  @Override
  public void run() {
    while (true) {
      table.smoke(resource);
    }
  }
}
