package org.levelup.threads.funnylab.supermarket;

import lombok.SneakyThrows;

import java.util.Random;

public class Supermarket {

  private boolean[] busy = new boolean[2];
  private int[] queueSize = new int[2];

  @SneakyThrows
  public synchronized void enqueue() {
    Random r = new Random();
    int employeeId = r.nextInt(2);
    queueSize[employeeId]++;
    if (!busy[employeeId]) {
      busy[employeeId] = true;
      System.out.println("Employee " + employeeId + " is awoken by customer: " + Thread.currentThread().getName());
      notifyAll();
      return;
    }
    while (busy[employeeId]) {
      wait();
    }
    busy[employeeId] = true;
    System.out.println("Customer " + Thread.currentThread().getName() + " awoken by employee " + employeeId);
    wait();
  }

  @SneakyThrows
  public void dequeue(int id) {

    synchronized (this) {
      while (!busy[id]) {
        System.out.println("Employee " + id + " is sleeping");
        wait();
      }
    }

    System.out.println("Employee " + id + ": Working with customer... ");
    for (int i = 5; i > 0; i--) {
      System.out.println("Employee " + id + ": " + i + "...");
      Thread.sleep(1000);
    }
    System.out.println("Employee " + id + ": Finished.");

    synchronized (this) {
      busy[id] = false;
      queueSize[id]--;
      notifyAll();
    }
    Thread.sleep(500);

  }

}
