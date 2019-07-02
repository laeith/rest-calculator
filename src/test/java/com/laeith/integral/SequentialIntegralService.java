package com.laeith.integral;

/**
 * This class is intended purely for tests, particularly for comparison with its
 * parallelized version {@link IntegralCalculator#calculateEToXIntegral(double, double, int, long)}
 * in {@link IntegralCalculatorBenchmark}.
 */
class SequentialIntegralService {

  double calculateSequentially(double startInterval, double stopInterval, long subintervals) {
    final double divisionSize = (stopInterval - startInterval) / subintervals;

    double result = 0;
    double curr_start = startInterval;
    while (curr_start <= stopInterval - divisionSize) {
      result += calculateAreaForSubinterval(divisionSize, curr_start);
      curr_start += divisionSize;
    }
    return result;
  }

  private double calculateAreaForSubinterval(double divisionSize, double intervalStart) {
    return Math.pow(Math.E, intervalStart) * divisionSize;
  }
}
