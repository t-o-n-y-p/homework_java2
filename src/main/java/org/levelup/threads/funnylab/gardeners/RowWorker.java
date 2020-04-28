package org.levelup.threads.funnylab.gardeners;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RowWorker implements Runnable {

  private final Garden garden;

  @Override
  public void run() {
    for (int i = 0; i < garden.getHeight(); i++) {
      for (int j = 0; j < garden.getWidth(); j++) {
        if (garden.process(i, j)) {
          System.out.println(Thread.currentThread().getName() + ": processed cell " + i + " " + j);
        }
      }
    }
  }
}
