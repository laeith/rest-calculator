package com.laeith.calculator;

import com.laeith.calculator.dto.CalculatorHistoryEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculatorService {

  private final CalculatorHistoryDAO historyDAO;

  @Autowired
  public CalculatorService(CalculatorHistoryDAO historyDAO) {
    this.historyDAO = historyDAO;
  }

  /**
   * Accepts mathematical expression as a string for input.
   * Assumes that provided expression is a valid one, if not then throws ExpressionException.
   * Operands and operators may be separated by arbitrary number of spaces.
   * <p>
   * Throws a runtime exception {@link com.udojava.evalex.Expression.ExpressionException}
   *
   * @param input
   * @return
   */
  public String calculate(String input) {
    return Calculator.calculate(input).toPlainString();
  }

  /**
   * In addition to {@link CalculatorService#calculate(String)} this method persists computation
   * details (if successful) to database.
   */
  @Transactional
  public String calculateAndSave(String input) {
    var result = calculate(input);

    var historyEntry = CalculatorHistoryEntry.builder()
       .input(input)
       .output(result)
       .computedAtUTC(Instant.now())
       .build();

    historyDAO.save(historyEntry);

    return result;
  }

  public List<CalculatorHistoryEntryDTO> retrieveCalculationHistory() {
    return historyDAO.getAll().stream()
       .map(CalculatorHistoryEntry::toDto)
       .collect(Collectors.toList());
  }

}
