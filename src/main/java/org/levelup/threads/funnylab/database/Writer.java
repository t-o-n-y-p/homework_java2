package org.levelup.threads.funnylab.database;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class Writer implements Runnable {

  private final Database database;

  @Override
  @SneakyThrows
  public void run() {
    database.write();
  }
}
