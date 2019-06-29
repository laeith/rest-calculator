package com.laeith.infrastructure.web;

import lombok.Value;

@Value
public class RESTError {
  private final String message;
  private final String description;
}
