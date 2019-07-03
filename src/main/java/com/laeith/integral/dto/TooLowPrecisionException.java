package com.laeith.integral.dto;

public class TooLowPrecisionException extends RuntimeException {
  public TooLowPrecisionException(String reason) {
    super(reason);
  }
}
