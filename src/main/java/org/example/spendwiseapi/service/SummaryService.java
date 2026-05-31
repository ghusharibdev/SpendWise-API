package org.example.spendwiseapi.service;

import lombok.RequiredArgsConstructor;
import org.example.spendwiseapi.dto.*;
import org.example.spendwiseapi.model.*;
import org.example.spendwiseapi.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final TransactionRepository transactionRepository;

    public MonthlySummaryResponse getMonthlySummary(Integer year, Integer month, Authentication authentication) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        var transactions = transactionRepository.findByUserEmailAndDateBetweenOrderByDateDescIdDesc(authentication.getName(), start, end);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;
        Map<String, BigDecimal> expenseByCategory = new LinkedHashMap<>();

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.INCOME) {
                totalIncome = totalIncome.add(transaction.getAmount());
            } else {
                totalExpense = totalExpense.add(transaction.getAmount());
                String categoryName = transaction.getCategory().getName();
                expenseByCategory.put(
                        categoryName,
                        expenseByCategory.getOrDefault(categoryName, BigDecimal.ZERO).add(transaction.getAmount())
                );
            }
        }

        return new MonthlySummaryResponse(
                year,
                month,
                totalIncome,
                totalExpense,
                totalIncome.subtract(totalExpense),
                expenseByCategory
        );
    }
}