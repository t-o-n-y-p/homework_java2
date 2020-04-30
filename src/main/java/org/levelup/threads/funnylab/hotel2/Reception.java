package org.levelup.threads.funnylab.hotel2;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class Reception implements Runnable {

  private final Hotel hotel;

  @Override
  @SneakyThrows
  public void run() {
    while (true) {
      Thread.sleep(hotel.getRooms() * 500);
      hotel.checkOut();
    }
  }
}
