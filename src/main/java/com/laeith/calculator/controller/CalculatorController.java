package com.laeith.calculator.controller;

import com.laeith.calculator.CalculatorService;
import com.laeith.calculator.dto.CalculatorHistoryEntryDTO;
import com.laeith.infrastructure.web.GenericResponse;
import com.laeith.infrastructure.web.RESTError;
import com.udojava.evalex.Expression;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
class CalculatorController {
  private static final Logger LOG = LogManager.getLogger(CalculatorController.class);
  private final CalculatorService calculatorService;

  @Autowired
  CalculatorController(CalculatorService calculatorService) {
    this.calculatorService = calculatorService;
  }

  @PostMapping(value = "/evaluate")
  @ApiOperation("Evaluates math expressions")
  public GenericResponse<String> evaluateExpression(
     @ApiParam(value = "Math expression for evaluation", example = "5-3^(2+1)")
     @RequestParam String expression
  ) {
    return new GenericResponse<>(calculatorService.calculateAndSave(expression));
  }

  @GetMapping(value = "/history")
  @ApiOperation("Returns list of all calculations done so far")
  public GenericResponse<List<CalculatorHistoryEntryDTO>> evaluateExpression() {
    return new GenericResponse<>(calculatorService.retrieveCalculationHistory());
  }

  @ExceptionHandler(Expression.ExpressionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GenericResponse<RESTError> handleIncorrectExpression(Expression.ExpressionException ex) {
    LOG.info("Provided mathematical expression is incorrect", ex);
    return new GenericResponse<>("Provided mathematical expression is incorrect",
       new RESTError(ex.getMessage()));
  }

  @ExceptionHandler(ArithmeticException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GenericResponse<RESTError> handleArithmeticException(ArithmeticException ex) {
    LOG.info("Calculation failed due to ArithmeticException", ex);
    return new GenericResponse<>("Arithmetic exception thrown", new RESTError(ex.getMessage()));
  }

}
