package org.levelup.threads.pool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolApp {

  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    for (int i = 0; i < 100; i++) {
      executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
    }

    for (int i = 0; i < 10; i++) {
      Future<Integer> future = executorService.submit(() -> {
        Random random = new Random();
        return random.nextInt(10);
      });
    }

    executorService.shutdown();

  }

}
