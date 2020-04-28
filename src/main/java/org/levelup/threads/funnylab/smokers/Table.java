package org.levelup.threads.funnylab.smokers;

import lombok.SneakyThrows;

import java.util.*;

public class Table {

  private Collection<String> resources = new HashSet<>();

  @SneakyThrows
  public synchronized void generateResources() {
    while (resources.size() > 0) {
      wait();
    }
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
    notifyAll();
  }

  @SneakyThrows
  public synchronized void smoke(String resource) {
    while (resources.size() < 2 || resources.contains(resource)) {
      wait();
    }
    resources.clear();
    System.out.println("Smoker with " + resource + " starts smoking...");
    Thread.sleep(2000);
    System.out.println("Finished.");
    notify();
  }

}
