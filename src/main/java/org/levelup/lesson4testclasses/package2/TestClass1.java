package org.levelup.lesson4testclasses.package2;

public class TestClass1 {

  private int testField1;
  private int testField2;
  private int testField3;

  public TestClass1() {
    this.testField1 = 61;
    this.testField2 = 62;
    this.testField3 = 63;
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

