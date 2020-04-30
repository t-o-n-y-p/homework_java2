package org.levelup.threads.funnylab.hotel2;

import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;

public class Hotel {

  private final RoomBlock[] roomBlocks;

  private final int rooms;

  public Hotel(RoomBlock... roomBlocks) {
    this.roomBlocks = roomBlocks;
    this.rooms = Arrays.stream(this.roomBlocks).mapToInt(RoomBlock::getRooms).sum();
  }

  @SneakyThrows
  public synchronized void checkIn(int money) {
    System.err.println(Thread.currentThread().getName() + ": Money: " + money);
    List<RoomBlock> roomServicesList = Arrays.stream(roomBlocks)
        .filter(s -> s.getPrice() <= money && s.getRooms() > 0)
        .sorted()
        .collect(Collectors.toList());
    if (roomServicesList.isEmpty()) {
      System.err.println(Thread.currentThread().getName() + ": No free rooms for me");
    } else {
      RoomBlock currentBlock = roomServicesList.get(0);
      currentBlock.decrementRooms();
      System.err.println(Thread.currentThread().getName() + ": Checked in! Room price: " + currentBlock.getPrice()
          + ", rooms left: " + currentBlock.getRooms());
      wait();
      System.err.println(Thread.currentThread().getName() + ": Leaving hotel...");
      currentBlock.incrementRooms();
    }
  }

  @SneakyThrows
  public synchronized void checkOut() {
    notifyAll();
  }

  public int getRooms() {
    return rooms;
  }

}
