package org.levelup.threads.funnylab.barbershop;

import lombok.SneakyThrows;

public class BarberShop {

  private boolean busy = false;
  private int queueSize = 0;

  @SneakyThrows
  public synchronized void enqueue() {
    queueSize++;
    if (!busy) {
      busy = true;
      System.out.println("Barber is awoken by client: " + Thread.currentThread().getName());
      notify();
      System.out.println("Client " + Thread.currentThread().getName() + " is sleeping");
      wait();
      return;
    }
    while (busy) {
      wait();
    }
    busy = true;
    System.out.println("Client " + Thread.currentThread().getName() + " awoken by barber");
    System.out.println("Client " + Thread.currentThread().getName() + " is sleeping");
    wait();
  }

  @SneakyThrows
  public void dequeue() {

    synchronized (this) {
      while (!busy) {
        System.out.println("Barber is sleeping");
        wait();
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
      queueSize--;
      for (int i = 0; i <= queueSize; i++) {
        notify();
      }
    }
    Thread.sleep(500);

  }

}
