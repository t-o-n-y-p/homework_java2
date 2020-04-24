package org.levelup.threads.counter;

import lombok.SneakyThrows;

public class CounterThread implements Runnable {

  private Counter counter;

  public CounterThread(Counter counter) {
    this.counter = counter;
  }

  @Override
  @SneakyThrows
  public void run() {


    while (counter.getCounter() < 30 && !Thread.currentThread().isInterrupted()) {
      counter.incrementCounter();
      System.out.println("Counter value from runnable " + Thread.currentThread().getName() + ": " + counter.getCounter());
    }

  }
}
