package org.example.spendwiseapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.spendwiseapi.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
        @NotBlank String title,
        @NotNull @Positive BigDecimal amount,
        @NotNull TransactionType type,
        @NotNull LocalDate date,
        String note,
        @NotNull Long categoryId
) {
}