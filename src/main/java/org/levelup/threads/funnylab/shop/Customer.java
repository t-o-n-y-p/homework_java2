package org.levelup.threads.funnylab.shop;

import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class Customer implements Runnable {

  private final Shop shop;

  @Override
  public void run() {
    System.out.println("Customer " + Thread.currentThread().getName() + " created");
    List<Integer> employeeIds = new ArrayList<>(Arrays.asList(0, 1, 2));
    Random r = new Random();
    Integer nextEmployeeId;
    while (!employeeIds.isEmpty()) {
      nextEmployeeId = employeeIds.get(r.nextInt(employeeIds.size()));
      employeeIds.remove(nextEmployeeId);
      shop.enqueue(nextEmployeeId);
      if (r.nextDouble() > 0.5) {
        break;
      }
    }
    System.out.println("Customer " + Thread.currentThread().getName() + " is done.");
  }
}
