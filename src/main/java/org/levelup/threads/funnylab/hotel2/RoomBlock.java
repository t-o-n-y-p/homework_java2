package org.levelup.threads.funnylab.hotel2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomBlock implements Comparable<RoomBlock> {

  private final int price;
  private int rooms;

  public void decrementRooms() {
    rooms--;
  }

  public void incrementRooms() {
    rooms++;
  }

  @Override
  public int compareTo(RoomBlock o) {
    return Integer.compare(o.getPrice(), getPrice());
  }
}
