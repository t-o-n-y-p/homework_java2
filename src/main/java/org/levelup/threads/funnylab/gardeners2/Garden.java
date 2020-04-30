package org.levelup.threads.funnylab.gardeners2;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Garden {

  private final List<Flower> flowers = new ArrayList<>();

  public Garden(int numberOfFlowers) {
    for (int i = 0; i < numberOfFlowers; i++) {
      flowers.add(new Flower());
    }
  }

  @SneakyThrows
  public void revive() {
    synchronized (this) {
      List<Flower> rottenFlowers;
      while ((rottenFlowers = flowers.stream().filter(Flower::isRotten).collect(Collectors.toList())).isEmpty()) {
        wait();
      }
      Random r = new Random();
      rottenFlowers.get(r.nextInt(rottenFlowers.size())).setRotten(false);
      System.out.println(getFlowersState());
    }
    System.out.println(Thread.currentThread().getName() + ": Revived 1 flower.");
  }

  @SneakyThrows
  public synchronized void destroy() {
    Random r = new Random();
    int numberOfRottenFlowers = r.nextInt(5) + 1;
    for (int i = 0; i < numberOfRottenFlowers; i++) {
      flowers.get(i).setRotten(true);
    }
    Collections.shuffle(flowers);
    System.out.println("Flowers destroyed: " + numberOfRottenFlowers);
    System.out.println(getFlowersState());
    notifyAll();
  }

  private String getFlowersState() {
    return flowers.stream().map(f -> f.isRotten() ? "1" : "0").collect(Collectors.joining(" "));
  }

}
