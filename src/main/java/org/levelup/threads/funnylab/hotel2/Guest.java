package org.levelup.threads.funnylab.hotel2;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Guest implements Runnable {

  private final Hotel hotel;
  private final int money;

  @Override
  public void run() {
    hotel.checkIn(money);
  }
}
