package org.levelup.threads.funnylab.cannibals;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Chef implements Runnable {

  private final Pot pot;

  @Override
  public void run() {
    while (true) {
      pot.cook();
    }
  }
}
