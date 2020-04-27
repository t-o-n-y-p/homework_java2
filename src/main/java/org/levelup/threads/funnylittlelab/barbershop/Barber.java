package org.levelup.threads.funnylittlelab.barbershop;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class Barber implements Runnable {

  private final BarberShop barberShop;

  @Override
  @SneakyThrows
  public void run() {
    while (true) {
      barberShop.dequeue();
    }
  }
}
