package com.laeith.infrastructure.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Generic REST exception handler - in case of any 'unexpected' exceptions this will be invoked and
 * a generic error response will be served.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestController
@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ServletException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GenericResponse genericIncorrectRequestMethod(ServletException ex) {
    return new GenericResponse(ex.getMessage());
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public GenericResponse genericIllegalStateHandler(IllegalStateException ex) {
    LOG.error("IllegalStateException caught!", ex);
    return new GenericResponse(ex.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GenericResponse genericEntityNotFoundHandler(EntityNotFoundException ex) {
    return new GenericResponse(ex.getMessage());
  }

  @Order(Ordered.LOWEST_PRECEDENCE)
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse genericExceptionHandler(Exception ex) {
    LOG.error("Unhandled server-side exception", ex);
    return new GenericResponse("Unexpected server-side error encountered - " +
       ZonedDateTime.now(ZoneOffset.UTC));
  }
}
