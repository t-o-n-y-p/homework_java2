package org.levelup.threads.funnylab.smokers;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResourceManager implements Runnable {

  private final Table table;

  @Override
  public void run() {
    while (true) {
      table.generateResources();
    }
  }
}
