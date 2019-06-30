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
  private Long id;
  @NonNull
  private String input;
  @NonNull
  private String output;
  @NonNull
  @JsonSerialize(using = ToStringSerializer.class)
  private Instant computedAtUTC;

  // Required for Lombok <-> Jackson compatibility
  @JsonPOJOBuilder(withPrefix = "")
  public static class RepositoryDetailsDTOBuilder {
  }
}
