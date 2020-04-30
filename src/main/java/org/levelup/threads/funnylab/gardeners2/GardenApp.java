package org.levelup.threads.funnylab.gardeners2;

public class GardenApp {

  public static void main(String[] args) {

    Garden garden = new Garden(40);
    Thread destroyer = new Thread(new Destroyer(garden));

    for (int i = 0; i < 2; i++) {
      new Thread(new Gardener(garden)).start();
    }

    destroyer.start();

  }

}
