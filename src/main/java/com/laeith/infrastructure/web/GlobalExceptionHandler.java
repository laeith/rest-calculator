package com.laeith.infrastructure.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
public class GlobalExceptionHandler implements ErrorController {
  private static final Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);

  private final static String ERROR_PATH = "/error";

  /**
   * This controller's method handles any internal redirects to /error, in particular access
   * denied related issues.
   */
  @ExceptionHandler(ServletException.class)
  @RequestMapping(value = ERROR_PATH)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ApiIgnore
  public GenericResponse<RESTError> genericIncorrectRequestHandler(ServletException ex) {
    return new GenericResponse<>("Failed to serve request",
       new RESTError("Requested page doesn't exist / is forbidden")
    );
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GenericResponse<RESTError> genericEntityNotFoundHandler(EntityNotFoundException ex) {
    LOG.info("Entity not found", ex);
    return new GenericResponse<>("Entity not found",
       new RESTError(ex.getMessage())
    );
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public GenericResponse<RESTError> genericIllegalStateHandler(IllegalStateException ex) {
    LOG.error("IllegalStateException caught!", ex);
    return new GenericResponse<>("Internal server error",
       new RESTError("Illegal state problem")
    );
  }

  @Order(Ordered.LOWEST_PRECEDENCE)
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public GenericResponse<RESTError> genericExceptionHandler(Exception ex) {
    LOG.error("Unhandled server-side exception", ex);
    return new GenericResponse<>("Internal server error",
       new RESTError("Unexpected server-side error encountered - " +
          ZonedDateTime.now(ZoneOffset.UTC))
    );
  }

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }
}
