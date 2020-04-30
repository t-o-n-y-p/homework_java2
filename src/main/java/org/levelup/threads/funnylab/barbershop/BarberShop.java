package org.levelup.threads.funnylab.barbershop;

import lombok.SneakyThrows;

public class BarberShop {

  private boolean busy = false;

  private final Object barber = new Object();
  private final Object queue = new Object();
  private final Object currentClient = new Object();

  @SneakyThrows
  public void enqueue() {
    synchronized (queue) {
      if (busy) {
        while (busy) {
          queue.wait();
        }
        System.out.println("Client " + Thread.currentThread().getName() + " awoken by barber");
      } else {
        System.out.println("Barber is awoken by client: " + Thread.currentThread().getName());
        synchronized (barber) {
          barber.notify();
        }
      }
    }
    synchronized (this) {
      busy = true;
    }
    synchronized (currentClient) {
      System.out.println("Client " + Thread.currentThread().getName() + " is sleeping");
      currentClient.wait();
    }
  }

  @SneakyThrows
  public void dequeue() {

    synchronized (barber) {
      while (!busy) {
        System.out.println("Barber is sleeping");
        barber.wait();
      }
    }

    System.out.println("Working with client...");
    for (int i = 5; i > 0; i--) {
      System.out.println(i + "...");
      Thread.sleep(1000);
    }
    System.out.println("Finished.");

    synchronized (this) {
      busy = false;
    }
    synchronized (currentClient) {
      currentClient.notify();
    }
    synchronized (queue) {
      queue.notify();
    }
    Thread.sleep(500);

  }

}
