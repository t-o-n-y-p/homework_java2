package org.levelup.threads.funnylab.gallery;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Guard implements Runnable {

  private final Gallery gallery;

  @Override
  public void run() {
    while (true) {
      gallery.allowEntrance();
    }
  }
}
