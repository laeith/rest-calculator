package com.laeith.calculator.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.time.Instant;

@Builder
@Value
@JsonDeserialize(builder = CalculatorHistoryEntryDTO.CalculatorHistoryEntryDTOBuilder.class)
public class CalculatorHistoryEntryDTO {
  @NonNull
  private final Long id;
  @NonNull
  private final String input;
  @NonNull
  private final String output;
  @JsonSerialize(using = ToStringSerializer.class)
  private final Instant computedAtUTC;

  // Required for Lombok <-> Jackson compatibility
  @JsonPOJOBuilder(withPrefix = "")
  public static class RepositoryDetailsDTOBuilder {
  }
}
