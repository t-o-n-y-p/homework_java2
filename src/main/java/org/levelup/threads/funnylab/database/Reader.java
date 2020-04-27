package org.levelup.threads.funnylab.database;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Reader implements Runnable {

  private final Database database;

  @Override
  public void run() {
    database.readByReader();
  }
}
