package org.levelup.threads.funnylab.pooh2;

public class PoohApp {

  public static final int bees = 10;

  public static void main(String[] args) {

    Hive hive = new Hive(bees);
    Thread pooh = new Thread(new Pooh(hive));
    pooh.start();

    for (int i = 0; i < bees; i++) {
      new Thread(new Bee(hive)).start();
    }

  }

}
