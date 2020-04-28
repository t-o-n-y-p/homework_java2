package org.levelup.threads.funnylab.philosophers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;

@AllArgsConstructor
public class Philosopher implements Runnable {

  private final Fork leftFork;
  private final Fork rightFork;

  @Override
  @SneakyThrows
  public void run() {
    Random r = new Random();
    while (true) {
      Thread.sleep(r.nextInt(10000) + 1);
      System.out.println(Thread.currentThread().getName() + ": I'm starving");
      leftFork.take();
      System.out.println(Thread.currentThread().getName() + ": Left fork taken");
      rightFork.take();
      System.out.println(Thread.currentThread().getName() + ": Right fork taken");
      System.out.println(Thread.currentThread().getName() + ": Eating...");
      Thread.sleep(5000);
      System.out.println(Thread.currentThread().getName() + ": Finished.");
      leftFork.release();
      rightFork.release();
    }
  }
}
