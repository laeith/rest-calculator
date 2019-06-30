package com.laeith.calculator.controller;

import com.laeith.calculator.CalculatorService;
import com.laeith.calculator.dto.CalculatorHistoryEntryDTO;
import com.laeith.infrastructure.web.GenericResponse;
import com.laeith.infrastructure.web.RESTError;
import com.udojava.evalex.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class CalculatorController {

  private final CalculatorService calculatorService;

  @Autowired
  CalculatorController(CalculatorService calculatorService) {
    this.calculatorService = calculatorService;
  }

  @PostMapping(value = "/evaluate")
  public GenericResponse<String> evaluateExpression(@RequestBody String expression) {
    return new GenericResponse<>(calculatorService.calculate(expression));
  }

  @GetMapping(value = "/history")
  public GenericResponse<List<CalculatorHistoryEntryDTO>> evaluateExpression() {
    return new GenericResponse<>(calculatorService.retrieveCalculationHistory());
  }

  @ExceptionHandler(Expression.ExpressionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RESTError handleIncorrectExpression(Expression.ExpressionException ex) {
    return new RESTError("Failed to parse provided mathematical expression.",
       ex.getMessage());
  }

}
