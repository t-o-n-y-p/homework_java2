package org.levelup.threads.funnylab.hotel;

import lombok.SneakyThrows;

public class Hotel {

  private final int rooms;
  private int freeRooms;

  private final Object queue = new Object();
  private final Object guests = new Object();

  public Hotel(int rooms) {
    this.rooms = rooms;
    this.freeRooms = rooms;
  }

  @SneakyThrows
  public void checkIn() {
    synchronized (queue) {
      while (freeRooms == 0) {
        System.out.println(Thread.currentThread().getName() + ": Waiting outside...");
        queue.wait();
      }
    }
    synchronized (this) {
      freeRooms--;
    }
    synchronized (guests) {
      System.out.println(Thread.currentThread().getName() + ": Checked in! Sleeping...");
      guests.wait();
      System.out.println(Thread.currentThread().getName() + ": Leaving the hotel...");
    }
    synchronized (this) {
      freeRooms++;
    }
  }

  @SneakyThrows
  public void checkOut() {
    while (true) {
      Thread.sleep(rooms * 400);
      synchronized (guests) {
        guests.notifyAll();
      }
      Thread.sleep(rooms * 200);
      synchronized (queue) {
        queue.notifyAll();
      }
    }
  }

}
