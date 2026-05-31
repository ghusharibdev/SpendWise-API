package org.example.spendwiseapi.repository;

import org.example.spendwiseapi.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserEmailOrderByMonthDesc(String email);
    Optional<Budget> findByIdAndUserEmail(Long id, String email);
    Optional<Budget> findByUserEmailAndCategoryIdAndMonth(String email, Long categoryId, YearMonth month);
}