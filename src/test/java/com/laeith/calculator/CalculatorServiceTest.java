package com.laeith.calculator;

import com.laeith.test.utils.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CalculatorServiceTest extends IntegrationTest {

  @Autowired
  private CalculatorService calculatorService;

  @Test
  void calculateSimpleAddition() {
    assertThat(calculatorService.calculate("5 + 10"))
       .isEqualTo("15");
  }

  @Test
  void calculateSimpleAdditionWithNegatives() {
    assertThat(calculatorService.calculate("10 + -5"))
       .isEqualTo("5");
    assertThat(calculatorService.calculate("5 + -10"))
       .isEqualTo("-5");
  }

  @Test
  void calculateSimpleDeduction() {
    assertThat(calculatorService.calculate("10 - 5"))
       .isEqualTo("5");
    assertThat(calculatorService.calculate("5 - 10"))
       .isEqualTo("-5");
  }

  @Test
  void calculateSimpleMultiplication() {
    assertThat(calculatorService.calculate("5 * 10"))
       .isEqualTo("50");
  }

  @Test
  void calculateSimpleDivision() {
    assertThat(calculatorService.calculate("10 / 5"))
       .isEqualTo("2");
  }

  @Test
  void calculateSimpleExponentiation() {
    assertThat(calculatorService.calculate("5 ^ 2"))
       .isEqualTo("25");
  }

  @Test
  void calculateSimpleSquareRoot() {
    assertThat(calculatorService.calculate("SQRT(36)"))
       .isEqualTo("6");
  }

  @Test
  void calculateSimpleExpressionWithBrackets() {
    assertThat(calculatorService.calculate("6 * (3 + 2)"))
       .isEqualTo("30");
    assertThat(calculatorService.calculate("(6 * 3) + 2"))
       .isEqualTo("20");
  }

  @Test
  void checkOperatorPrecedenceInCalculations() {
//    multiplication over addition
    assertThat(calculatorService.calculate("6 * 3 + 2"))
       .isEqualTo("20");

//    exponentiation over multiplication over addition
    assertThat(calculatorService.calculate("6 * 3 + 2"))
       .isEqualTo("20");

//    TODO: more tests, more tests...
  }

  @Test
  void checkDivideByZero() {
    assertThrows(ArithmeticException.class, () -> {
      calculatorService.calculate("5/0");
    });
  }

//  TODO: many, many more tests

}