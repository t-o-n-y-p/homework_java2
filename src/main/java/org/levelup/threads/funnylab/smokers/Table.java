package org.levelup.threads.funnylab.smokers;

import lombok.SneakyThrows;

import java.util.*;

public class Table {

  private Collection<String> resources = new HashSet<>();

  private final Object smokers = new Object();
  private final Object resourceManager = new Object();

  @SneakyThrows
  public void generateResources() {
    synchronized (resourceManager) {
      while (resources.size() > 0) {
        resourceManager.wait();
      }
    }
    synchronized (this) {
      Random r = new Random();
      int index;
      if ((index = r.nextInt(3)) == 0) {
        resources.add("tobacco");
        resources.add("paper");
      } else if (index == 1) {
        resources.add("tobacco");
        resources.add("sticks");
      } else if (index == 2) {
        resources.add("paper");
        resources.add("sticks");
      }
      System.out.println("Generated: " + resources);
    }
    synchronized (smokers) {
      smokers.notifyAll();
    }
  }

  @SneakyThrows
  public void smoke(String resource) {
    synchronized (smokers) {
      while (resources.size() < 2 || resources.contains(resource)) {
        smokers.wait();
      }
    }
    synchronized (this) {
      resources.clear();
      System.out.println("Smoker with " + resource + " starts smoking...");
      Thread.sleep(2000);
      System.out.println("Finished.");
    }
    synchronized (resourceManager) {
      resourceManager.notify();
    }
  }

}
