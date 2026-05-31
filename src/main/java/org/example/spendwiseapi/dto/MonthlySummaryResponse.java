package org.example.spendwiseapi.dto;

import java.math.BigDecimal;
import java.util.Map;

public record MonthlySummaryResponse(
        Integer year,
        Integer month,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance,
        Map<String, BigDecimal> expenseByCategory
) {
}