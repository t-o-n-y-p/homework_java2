package org.levelup.lesson4testclasses.package1;

public class TestClass1 {

  private int testField1;
  private int testField2;
  private int testField3;

  public TestClass1() {
    this.testField1 = 81;
    this.testField2 = 82;
    this.testField3 = 83;
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
