package com.laeith.integral;

import com.laeith.integral.dto.IntegralCalculationException;
import com.laeith.integral.dto.IntegralComputationTimeoutException;
import com.laeith.integral.dto.TooLowPrecisionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.OptionalDouble;
import java.util.concurrent.*;
import java.util.stream.LongStream;

@Service
class IntegralCalculator {
  private static final Logger LOG = LogManager.getLogger(IntegralCalculator.class);

  private static final int MAX_COMPUTATION_TIME_SECONDS = 2;

  /**
   * Calculates e^x integral for a given interval.
   * <p>
   * Implementation uses left Riemann sum. It amounts to an overestimation if f is monotonically
   * decreasing on this interval, and an underestimation if it is monotonically increasing.
   * In case of our e^x integral, it's always underestimated.
   * <p>
   * Maximum number of processing units must be in range 1-32767
   * Lower interval bound must be smaller than upper interval bound
   *
   * @param lowerIntervalBound
   * @param upperIntervalBound
   * @param processingUnits    number of threads used for calculation
   * @param subintervals       number of subintervals used for integral calculation in Riemann sum
   * @return
   */
  double calculateEToXIntegral(final double lowerIntervalBound, final double upperIntervalBound,
                               final int processingUnits, final long subintervals) {
    if (processingUnits > 32767 || processingUnits < 1) {
      throw new IllegalArgumentException("Processing units must be in range 1 - 32767");
    }
    if (upperIntervalBound < lowerIntervalBound) {
      throw new IllegalArgumentException("Upper interval bound must be bigger than lower interval" +
         " bound while it was: " + upperIntervalBound + " <= " + lowerIntervalBound);
    }
    if (subintervals <= 0) {
      throw new IllegalArgumentException("Subintervals must be a positive long value");
    }

    final var forkJoinPool = new ForkJoinPool(processingUnits);
    final double divisionSize = (upperIntervalBound - lowerIntervalBound) / subintervals;

    ForkJoinTask<OptionalDouble> computationTask = forkJoinPool.submit(() -> {
      return LongStream.range(0, subintervals).parallel()
         .mapToDouble(subintervalNumber -> {
           double subintervalLowerBound = lowerIntervalBound + (subintervalNumber * divisionSize);
           return calculateAreaForSubinterval(divisionSize, subintervalLowerBound);
         })
         .reduce(Double::sum);
    });

    try {
      OptionalDouble output = computationTask.get(MAX_COMPUTATION_TIME_SECONDS, TimeUnit.SECONDS);
      var result = output.orElseThrow(() -> new IntegralCalculationException("Failed to compute integral"));
      if (Double.isInfinite(result)) {
        throw new TooLowPrecisionException("Result is too big, calculator doesn't support " +
           "this kind of precision at the moment");
      } else {
        return result;
      }
    } catch (InterruptedException e) {
      LOG.error("ForkJoinTask was interrupted", e);
      throw new IntegralCalculationException("Integral calculation was interrupted");
    } catch (ExecutionException e) {
      LOG.error("Failed to compute integral correctly", e.getCause());
      throw new IntegralCalculationException(e.getCause().getMessage());
    } catch (TimeoutException e) {
      throw new IntegralComputationTimeoutException("Computation time exceeded " +
         MAX_COMPUTATION_TIME_SECONDS + " s, computation terminated");
    }
  }

  private double calculateAreaForSubinterval(double divisionSize, double intervalLowerBound) {
    return Math.pow(Math.E, intervalLowerBound) * divisionSize;
  }

}
