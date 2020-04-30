package org.levelup.threads.funnylab.gallery;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Random;

public class Gallery {

  private final int capacity = 50;
  private int currentState;
  private int[] attendersPerPicture = new int[5];
  private final double chanceToLeave = 0.2;

  private final Object guard = new Object();
  private final Object attenders = new Object();

  @SneakyThrows
  public void allowEntrance() {
    synchronized (guard) {
      while (currentState == capacity) {
        guard.wait();
      }
    }
    synchronized (attenders) {
      attenders.notifyAll();
    }
  }

  @SneakyThrows
  public void enter() {
    synchronized (attenders) {
      while (currentState == capacity) {
        attenders.wait();
      }
      currentState++;
      System.out.println("Attenders: " + currentState);
    }
    Random r = new Random();
    for (int i = 0; i < attendersPerPicture.length; i++) {
      synchronized (attenders) {
        attendersPerPicture[i]++;
        System.out.print("Attenders per picture: ");
        Arrays.stream(attendersPerPicture).forEach(p -> System.out.print(p + " "));
        System.out.println();
      }
      Thread.sleep(r.nextInt(2000) + 2000);
      synchronized (attenders) {
        attendersPerPicture[i]--;
      }
      if (r.nextDouble() > 1 - chanceToLeave) {
        break;
      }
    }
    synchronized (attenders) {
      currentState--;
      System.out.println("Attender has left.");
      System.out.println("Attenders: " + currentState);
      System.out.print("Attenders per picture: ");
      Arrays.stream(attendersPerPicture).forEach(p -> System.out.print(p + " "));
      System.out.println();
    }
    synchronized (guard) {
      guard.notify();
    }

  }

}
