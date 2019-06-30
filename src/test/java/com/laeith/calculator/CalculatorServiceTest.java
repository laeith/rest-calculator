package com.laeith.calculator;

import com.laeith.calculator.dto.CalculatorHistoryEntryDTO;
import com.laeith.test.utils.IntegrationTest;
import com.laeith.test.utils.QuickTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@QuickTest
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
    assertThat(calculatorService.calculate("6 ^ 2 * 2 + 2"))
       .isEqualTo("74");

//    TODO: add more precedence tests
  }

  @Test
  void checkDivideByZero() {
    assertThrows(ArithmeticException.class, () -> calculatorService.calculate("5/0"));
  }

  @Test
  void checkHistoryRetrieval() {
    var history = calculatorService.retrieveCalculationHistory();

    // Given DTO must match data pre-populated from test_data.sql
    CalculatorHistoryEntryDTO dto1 = CalculatorHistoryEntryDTO.builder()
       .id(1L)
       .input("5 + 3")
       .output("8")
       .computedAtUTC(Instant.parse("2019-05-29T12:52:28Z"))
       .build();

    assertThat(history).isNotNull()
       .isNotEmpty()
       .contains(dto1);
  }

  @Test
  void checkHistoryUpdate() {
    var oldHistory = calculatorService.retrieveCalculationHistory();

    calculatorService.calculateAndSave("10+17");

    var newHistory = calculatorService.retrieveCalculationHistory();

    assertThat(newHistory).isNotNull()
       .isNotEmpty();
    assertThat(newHistory.size()).isEqualTo(oldHistory.size() + 1);

    var newEntry = newHistory.get(oldHistory.size());
    assertThat(newEntry.getInput()).isEqualTo("10+17");
    assertThat(newEntry.getOutput()).isEqualTo("27");
  }


}