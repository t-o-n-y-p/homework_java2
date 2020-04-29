package org.levelup.threads.stop;

import lombok.SneakyThrows;

public class ShutdownApp {

  @SneakyThrows
  public static void main(String[] args) {

    ThreadStop thread = new ThreadStop();
    thread.start();

    Thread.sleep(1000);
    new Thread(() -> thread.shutdown = true).start();

  }

}
