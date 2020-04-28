package org.levelup.threads.funnylab.gardeners;

public class GardenerApp {

  public static void main(String[] args) {

    Garden garden = new Garden();
    Thread rowWorker = new Thread(new RowWorker(garden));
    Thread columnWorker = new Thread(new ColumnWorker(garden));

    rowWorker.start();
    columnWorker.start();

  }

}
