package com.laeith.calculator;

import com.laeith.calculator.dto.CalculatorHistoryEntryDTO;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "computation_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CalculatorHistoryEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NonNull
  private String input;
  @NonNull
  private String output;
  @Nullable
  @Column(name = "author_ip")
  private String authorIp;
  @NonNull
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
