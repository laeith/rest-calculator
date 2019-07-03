package com.laeith.infrastructure.utils;

import com.laeith.test.utils.IntegrationTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class NumericParserTest extends IntegrationTest {

  @Test
  void parseDoubleCorrectly() {
    assertThat(NumericParser.parseDouble("2")).isEqualTo(2.0);
  }

  @Test
  void parseNegativeDoubleCorrectly() {
    assertThat(NumericParser.parseDouble("-2")).isEqualTo(-2.0);
  }

  @Test
  void shouldThrowNumberFormatExceptionForNaN() {
    var NaN = "5000000000".repeat(50);
    assertThrows(NumberFormatException.class, () -> NumericParser.parseDouble(NaN));
  }

  @Test
  void shouldThrowNumberFormatExceptionForIncorrectInput() {
    assertThrows(NumberFormatException.class, () -> {
      NumericParser.parseDouble("some random input 123");
    });
  }

}