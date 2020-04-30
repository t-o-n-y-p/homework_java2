package org.levelup.threads.funnylab.hospital;

import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor
public class Patient implements Runnable {

  private final Hospital hospital;

  @Override
  public void run() {
    System.out.println("Patient " + Thread.currentThread().getName() + " created");
    Random r = new Random();
    int nextDoctor = hospital.enqueue(r.nextInt(hospital.getNumberOfDoctorsOnDuty()));
    hospital.enqueue(nextDoctor);
    System.out.println("Patient " + Thread.currentThread().getName() + " is done.");
  }

}
