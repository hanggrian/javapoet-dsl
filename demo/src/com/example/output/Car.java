package com.example.output;

import java.lang.Override;
import java.lang.String;

class Car implements Vehicle {
  @Override
  public String getName() {
    return "Car";
  }

  @Override
  public int getWheelCount() {
    return 4;
  }
}
