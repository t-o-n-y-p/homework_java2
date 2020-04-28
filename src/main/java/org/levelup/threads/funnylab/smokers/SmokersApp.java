package org.levelup.threads.funnylab.smokers;

public class SmokersApp {

  public static void main(String[] args) {

    Table table = new Table();
    Thread resourceManager = new Thread(new ResourceManager(table));
    resourceManager.start();

    Thread tobaccoSmoker = new Thread(new Smoker(table, "tobacco"));
    Thread paperSmoker = new Thread(new Smoker(table, "paper"));
    Thread sticksSmoker = new Thread(new Smoker(table, "sticks"));

    tobaccoSmoker.start();
    paperSmoker.start();
    sticksSmoker.start();

  }

}
