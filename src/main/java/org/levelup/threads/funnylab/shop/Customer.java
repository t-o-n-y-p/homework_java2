package org.levelup.threads.funnylab.shop;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class Customer implements Runnable {

  private final Shop shop;
  private final double chanceToLeave = 0.5;

  @Override
  public void run() {
    System.out.println("Customer " + Thread.currentThread().getName() + " created");
    List<Integer> employeeIds = new ArrayList<>();
    for (int i = 0; i < shop.getNumberOfEmployees(); i++) {
      employeeIds.add(i);
    }
    Random r = new Random();
    Integer nextEmployeeId;
    while (!employeeIds.isEmpty()) {
      nextEmployeeId = employeeIds.get(r.nextInt(employeeIds.size()));
      employeeIds.remove(nextEmployeeId);
      shop.enqueue(nextEmployeeId);
      if (r.nextDouble() > 1 - chanceToLeave) {
        break;
      }
    }
    System.out.println("Customer " + Thread.currentThread().getName() + " is done.");
  }
}
