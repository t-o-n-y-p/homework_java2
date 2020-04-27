package org.levelup.threads.funnylab.barbershop;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BarberShopApp {

  @SneakyThrows
  public static void main(String[] args) {

    BarberShop barberShop = new BarberShop();
    Barber barber = new Barber(barberShop);
    Thread barberThread = new Thread(barber);
    barberThread.setDaemon(true);
    barberThread.start();

    Random r = new Random();
    for (int i = 0; i < 30; i++) {
      TimeUnit.SECONDS.sleep(r.nextInt(10) + 1);
      Client client = new Client(barberShop);
      new Thread(client).start();
    }

  }

}
