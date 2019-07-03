package com.laeith.infrastructure.utils;


/**
 * Helper methods for parsing Strings to Numeric objects with custom exception creation.
 */
public class NumericParser {

  private NumericParser() {
  }

  public static double parseDouble(String input) {
    try {
      double result = Double.parseDouble(input);
      if (!Double.isFinite(result)) {
        throw new NumberFormatException("Incorrect numeric value: '" + input + "'");
      } else {
        return result;
      }
    } catch (NumberFormatException ex) {
      throw new NumberFormatException("Incorrect numeric value: '" + input + "'");
    }
  }
}
