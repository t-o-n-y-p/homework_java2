package org.levelup.threads;

import lombok.SneakyThrows;

import java.io.*;

public class Calculator {

  @SneakyThrows
  public synchronized static void accumulateResult(double result) {
    File outputFile = new File("out.txt");
    BufferedReader bufferedReader = new BufferedReader(new FileReader(outputFile));
    double currentValue = Double.parseDouble(bufferedReader.readLine());
    bufferedReader.close();
    currentValue += result;
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
    bufferedWriter.write(String.valueOf(currentValue));
    bufferedWriter.close();
  }

}
