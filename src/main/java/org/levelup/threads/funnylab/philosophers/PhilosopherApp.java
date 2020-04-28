package org.levelup.threads.funnylab.philosophers;

public class PhilosopherApp {

  private static final int numberOfPhilosophers = 5;

  public static void main(String[] args) {

    Fork[] forks = new Fork[numberOfPhilosophers];

    for (int i = 0; i < numberOfPhilosophers; i++) {
      forks[i] = new Fork();
    }

    Thread[] philosophers = new Thread[numberOfPhilosophers];

    for (int i = 0; i < numberOfPhilosophers; i++) {
      philosophers[i] = new Thread(
          new Philosopher(forks[i % numberOfPhilosophers], forks[(i + 1) % numberOfPhilosophers])
      );
    }

    for (Thread philosopher : philosophers) {
      philosopher.start();
    }

  }

}
