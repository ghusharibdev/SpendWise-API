package org.example.spendwiseapi.dto;


import org.example.spendwiseapi.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        String title,
        BigDecimal amount,
        TransactionType type,
        LocalDate date,
        String note,
        Long categoryId,
        String categoryName
) {
}