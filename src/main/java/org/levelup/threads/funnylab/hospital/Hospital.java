package org.levelup.threads.funnylab.hospital;

import lombok.SneakyThrows;

import java.util.Random;

public class Hospital {

  private boolean[] busy = new boolean[5];
  private int[] queueSize = new int[5];

  @SneakyThrows
  public synchronized int enqueue(Integer doctorId) {
    queueSize[doctorId]++;
    if (!busy[doctorId]) {
      busy[doctorId] = true;
      System.out.println("Doctor " + doctorId + " is awoken by patient: " + Thread.currentThread().getName());
      notifyAll();
      while (busy[doctorId]) {
        wait();
      }
      if (doctorId < 2) {
        Random r = new Random();
        return r.nextInt(3) + 2;
      }
      return 0;
    }
    while (busy[doctorId]) {
      wait();
    }
    busy[doctorId] = true;
    System.out.println("Patient " + Thread.currentThread().getName() + " awoken by doctor " + doctorId);
    while (busy[doctorId]) {
      wait();
    }
    if (doctorId < 2) {
      Random r = new Random();
      return r.nextInt(3) + 2;
    }
    return 0;
  }

  @SneakyThrows
  public void dequeue(int id) {

    synchronized (this) {
      while (!busy[id]) {
        wait();
      }
    }

    System.out.println("Doctor " + id + ": Working with patient... ");
    for (int i = 5; i > 0; i--) {
      System.out.println("Doctor " + id + ": " + i + "...");
      Thread.sleep(1000);
    }
    System.out.println("Doctor " + id + ": Finished.");

    synchronized (this) {
      busy[id] = false;
      queueSize[id]--;
      notifyAll();
    }
    Thread.sleep(500);

  }

}
