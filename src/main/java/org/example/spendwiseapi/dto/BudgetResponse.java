package org.example.spendwiseapi.dto;

import java.math.BigDecimal;

public record BudgetResponse(
        Long id,
        BigDecimal limitAmount,
        Integer year,
        Integer month,
        Long categoryId,
        String categoryName
) {
}