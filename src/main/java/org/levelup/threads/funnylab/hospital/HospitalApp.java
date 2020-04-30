package org.levelup.threads.funnylab.hospital;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HospitalApp {

  @SneakyThrows
  public static void main(String[] args) {

    int numberOfDoctorsOnDuty = 2;
    int numberOfDoctors = 3;
    Hospital hospital = new Hospital(numberOfDoctorsOnDuty, numberOfDoctors);
    for (int i = 0; i < numberOfDoctorsOnDuty + numberOfDoctors; i++) {
      new Thread(new Doctor(hospital, i)).start();
    }

    TimeUnit.SECONDS.sleep(1);
    Random r = new Random();
    for (int i = 0; i < 30; i++) {
      TimeUnit.SECONDS.sleep(r.nextInt(10) + 1);
      Patient patient = new Patient(hospital);
      new Thread(patient).start();
    }

  }

}
