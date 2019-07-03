package com.laeith.integral.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonDeserialize(builder = IntegralApproximationDTO.IntegralApproximationDTOBuilder.class)
public class IntegralApproximationDTO {

  private final double result;
  private final long computationTimeInMillis;

  // Required for Lombok <-> Jackson compatibility
  @JsonPOJOBuilder(withPrefix = "")
  public static class IntegralApproximationDTOBuilder {
  }
}
