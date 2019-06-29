package com.laeith.infrastructure.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;
import java.util.List;

/**
 * Basic REST response template - all rest responses are expected to use it.
 * Nulls are ignored during JSON serialization.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {
  private final String message;
  private final T data;
  private final List<RESTError> errors;

  public GenericResponse(String message, T data, List<RESTError> errors) {
    this.message = message;
    this.data = data;
    this.errors = errors;
  }

  public GenericResponse(String message, RESTError error) {
    this(message, null, Arrays.asList(error));
  }

  public GenericResponse(T data) {
    this(null, data, null);
  }

  public GenericResponse(String message) {
    this(message, null, null);
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public List<RESTError> getErrors() {
    return errors;
  }
}
