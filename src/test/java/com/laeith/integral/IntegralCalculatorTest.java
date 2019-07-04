package com.laeith.integral;

import com.laeith.integral.dto.IntegralComputationTimeoutException;
import com.laeith.integral.dto.TooLowPrecisionException;
import com.laeith.test.utils.QuickTest;
import com.laeith.test.utils.SlowTest;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Timeout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@QuickTest
class IntegralCalculatorTest {

  private final IntegralCalculator integralCalculator = new IntegralCalculator();

//  Results correctness tolerance
  private final double TOLERANCE = 0.1;

  @Test
  void shouldReturnCorrectResultsWithinSetTolerance() {
    var approximation = integralCalculator.calculateEToXIntegral(2, 5,
       4, 5_000);
    var exactResult = 141.02;
    assertThat(getAbsDifference(approximation, exactResult)).isBetween(-TOLERANCE, TOLERANCE);
  }

  @Test
  void shouldReturnCorrectResultForNegativeIntervals() {
//    One negative bound
    var approximation = integralCalculator.calculateEToXIntegral(-20, 0,
       4, 5_000);
    var exactResult = 0.99;
    assertThat(getAbsDifference(approximation, exactResult)).isBetween(-TOLERANCE, TOLERANCE);

//    Both bounds negative
    var approximation2 = integralCalculator.calculateEToXIntegral(-5, -1,
       4, 5_000);
    var exactResult2 = 0.36;
    assertThat(getAbsDifference(approximation2, exactResult2)).isBetween(-TOLERANCE, TOLERANCE);
  }

  @Test
  void shouldReturnZeroForEqualIntervalBounds() {
    assertThat(integralCalculator.calculateEToXIntegral(0, 0, 4, 5_000))
       .isEqualTo(0);

    assertThat(integralCalculator.calculateEToXIntegral(123, 123, 4, 5_000))
       .isEqualTo(0);

    assertThat(integralCalculator.calculateEToXIntegral(-123, -123, 4, 5_000))
       .isEqualTo(0);
  }

  @Test
  void shouldThrowIllegalArgumentDueToTooManyProcessingUnits() {
    assertThrows(IllegalArgumentException.class, () -> {
      integralCalculator.calculateEToXIntegral(2, 5,
         5_000_000, 500);
    });
  }

  @Test
  void shouldThrowIllegalArgumentDueToIncorrectIntervalBounds() {
    assertThrows(IllegalArgumentException.class, () -> {
      integralCalculator.calculateEToXIntegral(5, 2,
         5_000_000, 500);
    });
  }

  @Test
  void shouldThrowIllegalArgumentExceptionOnNegativeSubintervals() {
    assertThrows(IllegalArgumentException.class, () -> {
      integralCalculator.calculateEToXIntegral(0, 5000,
         4, -500);
    });
  }

  @Test
  void shouldThrowIntegralCalculationExceptionDueToTooBigResult() {
    assertThrows(TooLowPrecisionException.class, () -> {
      integralCalculator.calculateEToXIntegral(0, 5000,
         4, 500);
    });
  }

  @Test
  @SlowTest
  @Timeout(time = 5)
  void shouldThrowIntegralComputationTooLongExceptionOnTimeout() {
    assertThrows(IntegralComputationTimeoutException.class, () -> {
      integralCalculator.calculateEToXIntegral(1, 300,
         1, Long.MAX_VALUE);
    });
  }

  private double getAbsDifference(double approximation, double exactResult) {
    return Math.abs(exactResult - approximation);
  }
}