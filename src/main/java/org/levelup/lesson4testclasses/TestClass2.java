package org.levelup.lesson4testclasses;

import randomint.RandomInt;

public class TestClass2 {

  @RandomInt(min = -16, max = 56)
  private int testField1;
  @RandomInt(min = 20, max = 38)
  private Integer testField2;
  // @RandomInt(min = 5, max = 25)
  private double testField3;

  @Override
  public String toString() {
    return "TestClass2{" +
        "testField1=" + testField1 +
        ", testField2=" + testField2 +
        ", testField3=" + testField3 +
        '}';
  }
}
