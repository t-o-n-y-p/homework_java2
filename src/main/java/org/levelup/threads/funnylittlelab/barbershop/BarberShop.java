package org.levelup.threads.funnylittlelab.barbershop;

import lombok.SneakyThrows;

public class BarberShop {

  private int numberOfClients = 0;

  @SneakyThrows
  public synchronized void queue() {
    if (numberOfClients == 0) {
      numberOfClients++;
      System.out.println("Barber is awoken by client: " + Thread.currentThread().getName());
      notify();
      System.out.println("Client " + Thread.currentThread().getName() + " is sleeping");
      wait();
      return;
    }
    while (numberOfClients > 0) {
      System.out.println("Client is sleeping");
      wait();
    }
    numberOfClients++;
    System.out.println("Client " + Thread.currentThread().getName() + " awoken by barber");
    System.out.println("Client " + Thread.currentThread().getName() + " is sleeping");
    wait();
  }

  @SneakyThrows
  public void dequeue() {
    synchronized (this) {
      while (numberOfClients < 1) {
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
      numberOfClients--;
      notify();
      notify();
    }
    Thread.sleep(1000);
  }

}
