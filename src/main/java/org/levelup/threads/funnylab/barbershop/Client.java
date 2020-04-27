package org.levelup.threads.funnylab.barbershop;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class Client implements Runnable {

  private final BarberShop barberShop;

  @Override
  @SneakyThrows
  public void run() {
    System.out.println("Client " + Thread.currentThread().getName() + " created");
    barberShop.queue();
    System.out.println("Client " + Thread.currentThread().getName() + " terminated by barber");
  }
}
