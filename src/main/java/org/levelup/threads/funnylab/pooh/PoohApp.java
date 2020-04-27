package org.levelup.threads.funnylab.pooh;

public class PoohApp {

  public static void main(String[] args) {

    Jar jar = new Jar();
    Thread poohThread = new Thread(new Pooh(jar));
    poohThread.setDaemon(true);
    poohThread.start();

    for (int i = 0; i < 10; i++) {
      Thread beeThread = new Thread(new Bee(jar));
      beeThread.start();
    }

  }

}
