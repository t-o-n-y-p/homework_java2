package org.levelup.threads.funnylittlelab.barbershop;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class BarberShopApp {

  @SneakyThrows
  public static void main(String[] args) {

    BarberShop barberShop = new BarberShop();
    Barber barber = new Barber(barberShop);
    new Thread(barber).start();

    for (int i = 0; i < 6; i++) {
      TimeUnit.MILLISECONDS.sleep(200);
      Client client = new Client(barberShop);
      new Thread(client).start();
    }

  }

}
