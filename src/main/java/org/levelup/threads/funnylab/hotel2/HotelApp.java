package org.levelup.threads.funnylab.hotel2;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HotelApp {

  @SneakyThrows
  public static void main(String[] args) {

    Hotel hotel = new Hotel(
        new RoomBlock(200, 10),
        new RoomBlock(400, 10),
        new RoomBlock(600, 5)
    );
    Thread reception = new Thread(new Reception(hotel));
    reception.start();

    Random r = new Random();
    for (int i = 0; i < 500; i++) {
      TimeUnit.MILLISECONDS.sleep(r.nextInt(500) + 1);
      Guest guest = new Guest(hotel, r.nextInt(600) + 100);
      new Thread(guest).start();
    }

  }

}
