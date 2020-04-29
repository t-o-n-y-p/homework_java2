package org.levelup.threads.queue;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantThreadSafeQueue<T> {

  private final Queue<T> queue;
  private final int maxSize;
  private final ReentrantLock lock;
  private final Condition notEmpty;
  private final Condition notFull;

  public ReentrantThreadSafeQueue(int maxSize) {
    this.maxSize = maxSize;
    this.queue = new LinkedList<>();
    this.lock = new ReentrantLock();

    this.notEmpty = lock.newCondition();
    this.notFull = lock.newCondition();
  }

  @SneakyThrows
  public void put(T task) {
    lock.lock();
    try {
      while (queue.size() == maxSize) {
        notFull.await();
      }
      queue.add(task);
      notEmpty.signalAll();
    } finally {
      lock.unlock();
    }
  }

  @SneakyThrows
  public T take() {
    lock.lock();
    try {
      while (queue.isEmpty()) {
        notEmpty.await();
      }
      T task = queue.remove();
      notFull.signalAll();
      return task;
    } finally {
      lock.unlock();
    }
  }


}
