package org.example.spendwiseapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BudgetRequest(
        @NotNull @Positive BigDecimal limitAmount,
        @NotNull Integer year,
        @NotNull Integer month,
        @NotNull Long categoryId
) {
}