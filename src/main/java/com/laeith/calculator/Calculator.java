package com.laeith.calculator;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

class Calculator {

  private Calculator() {}

  /**
   * Accepts valid mathematical equation specified in infix notation and returns its product.
   * Operands and operations may be separated by spaces.
   * <p>
   * Precision is limited to IEEE 754R Decimal32 format, 7 digits.
   * <p>
   * Possible operations:
   * - Addition: +
   * - Deduction: -
   * - Multiplication: *
   * - Division: /
   * - Exponentiation: b^n where b is base and n is a positive integer
   * - Square root: SQRT(n)
   * - Brackets: ()
   * - Comparisons: 3>2
   * <p>
   * Valid examples:
   * 2 + 7 * (1 + 3)
   * 2^3 / 7 + 3
   * SQRT(36)
   * 6^(7- 3)
   * 123
   * 5<8
   *
   * At the moment this uses EvalEx library for computation, in the future It will most likely
   * be changed to a custom implementation.
   *
   * @param infixNotation mathematical equation
   * @return product with trailing zeroes stripped
   */
  static BigDecimal calculate(String infixNotation) {
    Expression expression = new Expression(infixNotation);
    return expression.eval();
  }


}
