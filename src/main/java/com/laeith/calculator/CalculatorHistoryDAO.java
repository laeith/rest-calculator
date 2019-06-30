package com.laeith.calculator;

import com.laeith.infrastructure.persistence.GenericDAO;
import org.springframework.stereotype.Repository;

@Repository
class CalculatorHistoryDAO extends GenericDAO<CalculatorHistoryEntry> {
}
