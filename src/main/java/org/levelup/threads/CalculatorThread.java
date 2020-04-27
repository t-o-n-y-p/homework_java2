package org.levelup.threads;

import lombok.SneakyThrows;

import java.io.*;
import java.util.Arrays;

public class CalculatorThread implements Runnable {

  private final File inputFile;

  @SneakyThrows
  public CalculatorThread(File inputFile) {
    this.inputFile = inputFile;
  }

  @Override
  @SneakyThrows
  public void run() {
    BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
    int operationId = Integer.parseInt(bufferedReader.readLine());
    double[] arguments = Arrays.stream(bufferedReader.readLine().split("\\s"))
        .mapToDouble(Double::parseDouble)
        .toArray();
    double result = 0;
    if (operationId == 1) {
      result = Arrays.stream(arguments).sum();
    } else if (operationId == 2) {
      result = Arrays.stream(arguments).reduce(1, (a1, a2) -> a1 * a2);
    } else if (operationId == 3) {
      result = Arrays.stream(arguments).map(a -> a * a).sum();
    }
    bufferedReader.close();

    System.out.println(Thread.currentThread().getName() + ": " + result);
    Calculator.accumulateResult(result);

  }
}
