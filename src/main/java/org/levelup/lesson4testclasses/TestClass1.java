package org.levelup.lesson4testclasses;

public class TestClass1 {

  @RandomInt(min = -6, max = 128)
  private int testField1;
  private int testField2;
  private int testField3;

  public TestClass1() {
    this.testField2 = 12;
    this.testField3 = 13;
  }

  @Override
  public String toString() {
    return "TestClass1{" +
        "testField1=" + testField1 +
        ", testField2=" + testField2 +
        ", testField3=" + testField3 +
        '}';
  }
}
