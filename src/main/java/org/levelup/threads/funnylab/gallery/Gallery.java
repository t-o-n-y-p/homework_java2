package org.levelup.threads.funnylab.gallery;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Random;

public class Gallery {

  private final int capacity = 50;
  private int currentState;
  private int[] attendersPerPicture = new int[5];
  private final double chanceToLeave = 0.2;

  @SneakyThrows
  public synchronized void allowEntrance() {
    while (currentState == capacity) {
      wait();
    }
    notify();
  }

  @SneakyThrows
  public void enter() {
    synchronized (this) {
      while (currentState == capacity) {
        wait();
      }
      currentState++;
    }
    Random r = new Random();
    for (int i = 0; i < attendersPerPicture.length; i++) {
      synchronized (this) {
        attendersPerPicture[i]++;
        System.out.println("Attenders: " + currentState);
        System.out.print("Attenders per picture: ");
        Arrays.stream(attendersPerPicture).forEach(p -> System.out.print(p + " "));
        System.out.println();
      }
      Thread.sleep(r.nextInt(2000) + 2000);
      synchronized (this) {
        attendersPerPicture[i]--;
      }
      if (r.nextDouble() > 1 - chanceToLeave) {
        break;
      }
    }
    synchronized (this) {
      currentState--;
      System.out.println("Attender has left.");
      System.out.println("Attenders: " + currentState);
      System.out.print("Attenders per picture: ");
      Arrays.stream(attendersPerPicture).forEach(p -> System.out.print(p + " "));
      System.out.println();
      notify();
    }

  }

}
