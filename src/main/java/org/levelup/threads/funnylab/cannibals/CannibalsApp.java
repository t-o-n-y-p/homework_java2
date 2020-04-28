package org.levelup.threads.funnylab.cannibals;

public class CannibalsApp {

  private static final int numberOfCannibals = 5;

  public static void main(String[] args) {

    Pot pot = new Pot();
    Thread chef = new Thread(new Chef(pot));
    chef.start();
    Thread[] cannibals = new Thread[numberOfCannibals];

    for (int i = 0; i < numberOfCannibals; i++) {
      cannibals[i] = new Thread(new Cannibal(pot));
      cannibals[i].start();
    }

  }

}
