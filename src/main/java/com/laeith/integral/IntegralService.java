package com.laeith.integral;

import com.laeith.integral.dto.IntegralApproximationDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegralService {
  private static final Logger LOG = LogManager.getLogger(IntegralService.class);

  private final IntegralCalculator integralCalculator;

  @Autowired
  public IntegralService(IntegralCalculator integralCalculator) {
    this.integralCalculator = integralCalculator;
  }

  public IntegralApproximationDTO computeEToXIntegralApproximation(
     final double lowerBound, final double upperBound,
     final int processingUnits, final long subintervals
  ) {
    LOG.info(String.format("Calculating e^x for with lower bound: %s, upper bound: %s" +
          ", processing units: %s, subintervals: %s", lowerBound, upperBound,
       processingUnits, subintervals));

    long start = System.nanoTime();
    var result = integralCalculator.calculateEToXIntegral(lowerBound, upperBound, processingUnits, subintervals);
    long estimatedComputationTime = (System.nanoTime() - start) / 1_000_000;

    return IntegralApproximationDTO.builder()
       .result(result)
       .computationTimeInMillis(estimatedComputationTime)
       .build();
  }

}
