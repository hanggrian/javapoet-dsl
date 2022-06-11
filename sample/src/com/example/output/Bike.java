package com.example.output;

import java.lang.Override;
import java.lang.String;

class Bike implements Vehicle {
  @Override
  public String getName() {
    return "Bike";
  }

  @Override
  public int getWheelCount() {
    return 2;
  }
}
