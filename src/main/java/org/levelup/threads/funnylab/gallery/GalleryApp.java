package org.levelup.threads.funnylab.gallery;

import lombok.SneakyThrows;

public class GalleryApp {

  @SneakyThrows
  public static void main(String[] args) {

    Gallery gallery = new Gallery();
    Thread guard = new Thread(new Guard(gallery));
    guard.start();

    for (int i = 0; i < 1000; i++) {
      new Thread(new Attender(gallery)).start();
    }

  }

}
