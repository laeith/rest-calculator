package com.laeith.calculator;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorService {

  /**
   * Accepts mathematical expression as a string for input.
   * Assumes that provided expression is a valid one, if not then throws ExpressionException.
   * Operands and operators may be separated by arbitrary number of spaces.
   *
   * Throws a runtime exception {@link com.udojava.evalex.Expression.ExpressionException}
   * @param input
   * @return
   */
  public String calculate(String input) {
    return Calculator.calculate(input).toPlainString();
  }

  /**
   * In addition to {@link CalculatorService#calculate(String)} this method persists computation
   * details (if successful) to database.
   */
  public String calculateAndSave(String input) {
    throw new IllegalStateException("Not implemented");
  }

  public List<String> retrieveCalculationHistory() {
    return new ArrayList<>();
  }

}
