package org.levelup.threads;

import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class CalculatorApp {

  @SneakyThrows
  public static void main(String[] args) {
    File outputFile = new File("out.txt");
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
    bufferedWriter.write(String.valueOf(0));
    bufferedWriter.close();
    generateFiles(10);

    File inputFile;
    int n = 1;
    while ((inputFile = new File("in_" + n + ".txt")).exists()) {
      new Thread(new CalculatorThread(inputFile)).start();
      n++;
    }
  }

  @SneakyThrows
  public static void generateFiles(int numberOfFiles) {
    for (int i = 1; i <= numberOfFiles; i++) {
      Random r = new Random();
      File inputFile = new File("in_" + i + ".txt");
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(inputFile));
      bufferedWriter.write(r.nextInt(3) + 1 + "\n");
      bufferedWriter.write(
          r.nextDouble() * r.nextInt(r.nextInt(20) + 1) + " "
          + r.nextDouble() * r.nextInt(r.nextInt(20) + 1) + "\n"
      );
      bufferedWriter.close();
    }
  }

}
