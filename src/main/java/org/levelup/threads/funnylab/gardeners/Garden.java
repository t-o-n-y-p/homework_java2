package org.levelup.threads.funnylab.gardeners;

public class Garden {

  private final int width = 15;
  private final int height = 25;
  private boolean[][] state = new boolean[height][width];

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean process(int x, int y) {
    if (!state[x][y]) {
      synchronized (this) {
        state[x][y] = true;
      }
      return true;
    }
    return false;
  }

}
