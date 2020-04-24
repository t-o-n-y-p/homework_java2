package org.levelup.threads.counter;

public class App {

  public static void main(String[] args) throws InterruptedException {
    Counter counter = new NonBlockingCounter();

    Thread t1 = new Thread(new CounterThread(counter));
    Thread t2 = new Thread(new CounterThread(counter));
    Thread t3 = new Thread(new CounterThread(counter));

    long start = System.nanoTime();

    t1.start();
    t2.start();
    t3.start();

    Thread.sleep(5);
    t1.interrupt();

    t1.join();
    t2.join();
    t3.join();

    long end = System.nanoTime();
    System.out.println(counter.getCounter());
    System.out.println("Execution time: " + (end - start));

  }

}
