package org.levelup.threads.funnylab.shop;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class Shop {

  private final boolean[] busy;
  private final int numberOfEmployees;

  private final List<Object> employees = new ArrayList<>();
  private final List<Object> queues = new ArrayList<>();
  private final List<Object> currentCustomers = new ArrayList<>();

  public Shop(int numberOfEmployees) {
    this.numberOfEmployees = numberOfEmployees;
    busy = new boolean[numberOfEmployees];
    for (int i = 0; i < numberOfEmployees; i++) {
      employees.add(new Object());
      queues.add(new Object());
      currentCustomers.add(new Object());
    }
  }

  @SneakyThrows
  public void enqueue(int employeeId) {
    synchronized (queues.get(employeeId)) {
      if (busy[employeeId]) {
        while (busy[employeeId]) {
          queues.get(employeeId).wait();
        }
        System.out.println("Customer " + Thread.currentThread().getName() + " awoken by employee " + employeeId);
      } else {
        System.out.println("Employee " + employeeId + " is awoken by customer: " + Thread.currentThread().getName());
        synchronized (employees.get(employeeId)) {
          employees.get(employeeId).notify();
        }
      }
      busy[employeeId] = true;
    }
    synchronized (currentCustomers.get(employeeId)) {
      currentCustomers.get(employeeId).wait();
    }
  }

  @SneakyThrows
  public void dequeue(int employeeId) {

    synchronized (employees.get(employeeId)) {
      while (!busy[employeeId]) {
        System.out.println("Employee " + employeeId + " is sleeping");
        employees.get(employeeId).wait();
      }
    }

    System.out.println("Employee " + employeeId + ": Working with client...");
    for (int i = 5; i > 0; i--) {
      System.out.println("Employee " + employeeId + ": " + i + "...");
      Thread.sleep(1000);
    }
    System.out.println("Employee " + employeeId + ": Finished.");

    synchronized (employees.get(employeeId)) {
      busy[employeeId] = false;
    }
    synchronized (currentCustomers.get(employeeId)) {
      currentCustomers.get(employeeId).notify();
    }
    synchronized (queues.get(employeeId)) {
      queues.get(employeeId).notify();
    }
    Thread.sleep(500);

  }

  public int getNumberOfEmployees() {
    return numberOfEmployees;
  }

}
