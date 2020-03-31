package org.levelup.reflection.annotation;

public class ServiceImpl implements Service {
  @Override
  @Profiling
  public void doSomething() {
    System.out.println("do something ...");
  }
}
