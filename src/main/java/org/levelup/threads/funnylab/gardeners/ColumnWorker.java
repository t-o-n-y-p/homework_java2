package org.levelup.threads.funnylab.gardeners;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ColumnWorker implements Runnable {

  private final Garden garden;

  @Override
  public void run() {
    for (int i = garden.getWidth() - 1; i >= 0; i--) {
      for (int j = garden.getHeight() - 1; j >= 0; j--) {
        if (garden.process(j, i)) {
          System.out.println(Thread.currentThread().getName() + ": processed cell " + j + " " + i);
        }
      }
    }
  }
}
