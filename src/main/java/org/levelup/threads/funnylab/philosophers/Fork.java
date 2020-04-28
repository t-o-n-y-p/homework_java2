package org.levelup.threads.funnylab.philosophers;

import lombok.SneakyThrows;

public class Fork {

  private boolean busy = false;

  @SneakyThrows
  public synchronized void take() {
    while (busy) {
      wait();
    }
    busy = true;
  }

  public synchronized void release() {
    busy = false;
    notify();
  }

}
