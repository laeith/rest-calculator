package com.laeith.integral.dto;

public class IntegralComputationTimeoutException extends RuntimeException {
  public IntegralComputationTimeoutException(String reason) {
    super(reason);
  }
}
