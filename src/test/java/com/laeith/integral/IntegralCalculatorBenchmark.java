package com.laeith.integral;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(2)
@Warmup(iterations = 2)
@Threads(1)
public class IntegralCalculatorBenchmark {

  private final IntegralCalculator integralCalculator = new IntegralCalculator();
  private final SequentialIntegralService seqIntegralService = new SequentialIntegralService();

  @State(Scope.Benchmark)
  public static class InputValues {
    public double lowerIntervalBound = 2;
    public double upperIntervalBound = 5;
    public int fourProcessingUnits = 4;
    public int singleProcessingUnit = 1;
    public long subIntervals = 5_000_000;
  }

  @Benchmark
  public void timeItParallel4units(InputValues inputValues, Blackhole blackhole) {
    var result = integralCalculator.calculateEToXIntegral(
       inputValues.lowerIntervalBound,
       inputValues.upperIntervalBound,
       inputValues.fourProcessingUnits,
       inputValues.subIntervals
    );
    blackhole.consume(result);
  }

  @Benchmark
  public void timeItParallel1unit(InputValues inputValues, Blackhole blackhole) {
    var result = integralCalculator.calculateEToXIntegral(
       inputValues.lowerIntervalBound,
       inputValues.upperIntervalBound,
       inputValues.singleProcessingUnit,
       inputValues.subIntervals
    );
    blackhole.consume(result);
  }

  @Benchmark
  public void timeItSequential(InputValues inputValues, Blackhole blackhole) {
    var result = seqIntegralService.calculateSequentially(
       inputValues.lowerIntervalBound,
       inputValues.upperIntervalBound,
       inputValues.subIntervals
    );
    blackhole.consume(result);
  }
}
