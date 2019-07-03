package com.laeith.integral.controller;

import com.laeith.infrastructure.utils.NumericParser;
import com.laeith.infrastructure.web.GenericResponse;
import com.laeith.infrastructure.web.RESTError;
import com.laeith.integral.IntegralService;
import com.laeith.integral.dto.IntegralApproximationDTO;
import com.laeith.integral.dto.IntegralComputationTimeoutException;
import com.laeith.integral.dto.TooLowPrecisionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integral")
class IntegralController {
  private static final Logger LOG = LogManager.getLogger(IntegralController.class);

  private final IntegralService integralService;

  @Autowired
  IntegralController(IntegralService integralService) {
    this.integralService = integralService;
  }

  //  Overflows?
  @PostMapping
  public GenericResponse<IntegralApproximationDTO> approximateEToXIntegral(
     @RequestParam String lowerBound,
     @RequestParam String upperBound,
     @RequestParam String processingUnits,
     @RequestParam String subintervals
  ) {
//    Manual parsing is required to avoid silent overflows during Jackson binding process
    var result = integralService.computeEToXIntegralApproximation(
       NumericParser.parseDouble(lowerBound),
       NumericParser.parseDouble(upperBound),
       Integer.parseInt(processingUnits),
       Long.parseLong(subintervals)
    );

    return new GenericResponse<>(result);
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GenericResponse<RESTError> genericNumberFormatExceptionHandler(NumberFormatException ex) {
    LOG.info("Failed to convert a string to a number", ex);
    return new GenericResponse<>("Failed to convert a string to a number",
       new RESTError(ex.getMessage())
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GenericResponse<RESTError> handleComputationTimeoutException(IllegalArgumentException ex) {
    LOG.info("Provided parameters are incorrect", ex);
    return new GenericResponse<>("Provided parameters are incorrect",
       new RESTError(ex.getMessage())
    );
  }

  @ExceptionHandler(TooLowPrecisionException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse<RESTError> handleTooLowPrecisionException(TooLowPrecisionException ex) {
    LOG.info("Calculator precision is too low for meaningful results", ex);
    return new GenericResponse<>("Integral can't be calculated correctly",
       new RESTError(ex.getMessage())
    );
  }

  @ExceptionHandler(IntegralComputationTimeoutException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse<RESTError> handleComputationTimeoutException(IntegralComputationTimeoutException ex) {
    return new GenericResponse<>("Computation took too long for provided parameters",
       new RESTError(ex.getMessage())
    );
  }

}
