package org.levelup.threads.funnylab.database;

import lombok.SneakyThrows;

public class Database {

  private int data;
  private int readers;
  private int writers;

  @SneakyThrows
  public synchronized void readByWriter() {
    System.out.println("Writer session begins");
    writers++;
    while (readers > 0) {
    }
    System.out.println(data);
    System.out.println("Writer session ends");
    writers--;
  }

  @SneakyThrows
  public synchronized void write() {
    System.out.println("Writer session begins");
    writers++;
    while (readers > 0) {
      System.out.println("Waiting for readers = 0");
      Thread.sleep(100);
    }
    Thread.sleep(1000);
    System.out.println(data);
    Thread.sleep(500);
    data++;
    Thread.sleep(500);
    System.out.println(data);
    Thread.sleep(1000);
    System.out.println("Writer session ends");
    writers--;
  }

  @SneakyThrows
  public void readByReader() {
    while (writers > 0) {
      System.out.println("Waiting for writers = 0");
      Thread.sleep(100);
    }
    readers++;
    System.out.println("Reader session begins");
    Thread.sleep(1000);
    System.out.println(data);
    System.out.println("Reader session ends");
    readers--;
  }

}
