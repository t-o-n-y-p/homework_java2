package org.levelup.threads.funnylab.hotel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Guest implements Runnable {

  private final Hotel hotel;

  @Override
  public void run() {
    hotel.checkIn();
  }
}
