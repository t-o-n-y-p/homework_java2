package org.levelup.threads.funnylab.database;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class DatabaseApp {

  @SneakyThrows
  public static void main(String[] args) {

    Database database = new Database();

    for (int i = 0; i < 20; i++) {
      TimeUnit.MILLISECONDS.sleep(100);
      new Thread(new Reader(database)).start();
    }
    new Thread(new Writer(database)).start();
    for (int i = 0; i < 20; i++) {
      TimeUnit.MILLISECONDS.sleep(100);
      new Thread(new Reader(database)).start();
    }

  }

}
