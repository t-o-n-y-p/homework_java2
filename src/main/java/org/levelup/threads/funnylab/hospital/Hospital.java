package org.levelup.threads.funnylab.hospital;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hospital {

  private final boolean[] busy;

  private final int numberOfDoctorsOnDuty;
  private final int numberOfDoctors;

  private final List<Object> doctors = new ArrayList<>();
  private final List<Object> queues = new ArrayList<>();
  private final List<Object> currentPatients = new ArrayList<>();

  public Hospital(int numberOfDoctorsOnDuty, int numberOfDoctors) {
    this.numberOfDoctorsOnDuty = numberOfDoctorsOnDuty;
    this.numberOfDoctors = numberOfDoctors;
    busy = new boolean[numberOfDoctorsOnDuty + numberOfDoctors];
    for (int i = 0; i < numberOfDoctorsOnDuty + numberOfDoctors; i++) {
      doctors.add(new Object());
      queues.add(new Object());
      currentPatients.add(new Object());
    }
  }

  @SneakyThrows
  public int enqueue(int doctorId) {
    synchronized (queues.get(doctorId)) {
      if (busy[doctorId]) {
        while (busy[doctorId]) {
          queues.get(doctorId).wait();
        }
        System.out.println("Patient " + Thread.currentThread().getName() + " awoken by doctor " + doctorId);
      } else {
        System.out.println("Doctor " + doctorId + " is awoken by patient: " + Thread.currentThread().getName());
        synchronized (doctors.get(doctorId)) {
          doctors.get(doctorId).notify();
        }
      }
      busy[doctorId] = true;
    }
    synchronized (currentPatients.get(doctorId)) {
      currentPatients.get(doctorId).wait();
      if (doctorId < numberOfDoctorsOnDuty) {
        Random r = new Random();
        return r.nextInt(numberOfDoctors) + numberOfDoctorsOnDuty;
      }
      return 0;
    }
  }

  @SneakyThrows
  public void dequeue(int doctorId) {

    synchronized (doctors.get(doctorId)) {
      while (!busy[doctorId]) {
        System.out.println("Doctor " + doctorId + " is sleeping");
        doctors.get(doctorId).wait();
      }
    }

    System.out.println("Doctor " + doctorId + ": Working with patient...");
    for (int i = 5; i > 0; i--) {
      System.out.println("Doctor " + doctorId + ": " + i + "...");
      Thread.sleep(1000);
    }
    System.out.println("Doctor " + doctorId + ": Finished.");

    synchronized (doctors.get(doctorId)) {
      busy[doctorId] = false;
    }
    synchronized (currentPatients.get(doctorId)) {
      currentPatients.get(doctorId).notify();
    }
    synchronized (queues.get(doctorId)) {
      queues.get(doctorId).notify();
    }
    Thread.sleep(500);

  }

  public int getNumberOfDoctorsOnDuty() {
    return numberOfDoctorsOnDuty;
  }

}
