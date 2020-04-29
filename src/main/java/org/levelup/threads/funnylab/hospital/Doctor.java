package org.levelup.threads.funnylab.hospital;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Doctor implements Runnable {

  private final Hospital hospital;
  private final int id;

  @Override
  public void run() {
    while (true) {
      hospital.dequeue(id);
    }
  }

}
