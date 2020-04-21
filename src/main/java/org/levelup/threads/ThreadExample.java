package org.levelup.threads;

import lombok.SneakyThrows;

public class ThreadExample {

  @SneakyThrows
  public static void main(String[] args) {

    Thread codeAnalyzer = new Thread(new BackgroundTask());
    codeAnalyzer.setDaemon(true);
    codeAnalyzer.start();

    Thread helloWorldThread = new Thread(new HelloWorldRunnable(), "hello-world-thread");
    helloWorldThread.start();

    Thread counter = new CounterThread();
    counter.start();
    Thread counter2 = new CounterThread();
    counter2.start();
    System.out.println("Main thread");
    String threadName = Thread.currentThread().getName();
    System.out.println("Thread name: " + threadName);

    Thread.sleep(13000);
    System.out.println("Main thread finished");

  }

  static class CounterThread extends Thread {

    @Override
    @SneakyThrows
    public void run() {
      String threadName = Thread.currentThread().getName();
      System.out.println("Thread name: " + threadName);
      System.out.println(threadName + ": start counter thread");
      for (int i = 0; i < 10; i++) {
        Thread.sleep(1000);
        System.out.println(threadName + ": " + i);
      }
    }
  }

  static class HelloWorldRunnable implements Runnable {

    @Override
    @SneakyThrows
    public void run() {
      Thread.sleep(4000);
      System.out.println("Hello from " + Thread.currentThread().getName());
    }
  }

  static class BackgroundTask implements Runnable {

    @Override
    @SneakyThrows
    public void run() {
      while (true) {
        Thread.sleep(500);
        System.out.println("Code analyzer started...");
      }
    }
  }

}
