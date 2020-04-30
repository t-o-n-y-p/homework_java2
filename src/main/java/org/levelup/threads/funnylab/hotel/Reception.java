package org.levelup.threads.funnylab.hotel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Reception implements Runnable {

  private final Hotel hotel;

  @Override
  public void run() {
    while (true) {
      hotel.checkOut();
    }
  }
}
