package com.laeith.calculator;

import com.laeith.calculator.dto.CalculatorHistoryEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "computation_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CalculatorHistoryEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String input;
  private String output;
  @Column(name = "author_ip")
  private String authorIp;
  @Column(name = "computed_at_utc")
  private Instant computedAtUTC;

  CalculatorHistoryEntryDTO toDto() {
    return CalculatorHistoryEntryDTO.builder()
       .id(id)
       .input(input)
       .output(output)
       .computedAtUTC(computedAtUTC)
       .build();
  }

}
