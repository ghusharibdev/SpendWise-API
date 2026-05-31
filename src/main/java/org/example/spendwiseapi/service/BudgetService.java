package org.example.spendwiseapi.service;


import lombok.RequiredArgsConstructor;
import org.example.spendwiseapi.dto.*;
import org.example.spendwiseapi.model.*;
import org.example.spendwiseapi.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final CurrentUserService currentUserService;

    public BudgetResponse create(BudgetRequest request, Authentication authentication) {
        AppUser user = currentUserService.getUser(authentication);

        Category category = categoryRepository.findByIdAndUserEmail(request.categoryId(), authentication.getName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        YearMonth month = YearMonth.of(request.year(), request.month());

        if (budgetRepository.findByUserEmailAndCategoryIdAndMonth(authentication.getName(), request.categoryId(), month).isPresent()) {
            throw new RuntimeException("Budget already exists for this category and month");
        }

        Budget budget = Budget.builder()
                .limitAmount(request.limitAmount())
                .month(month)
                .category(category)
                .user(user)
                .build();

        return map(budgetRepository.save(budget));
    }

    public List<BudgetResponse> getAll(Authentication authentication) {
        return budgetRepository.findByUserEmailOrderByMonthDesc(authentication.getName())
                .stream()
                .map(this::map)
                .toList();
    }

    public BudgetResponse update(Long id, BudgetRequest request, Authentication authentication) {
        Budget budget = budgetRepository.findByIdAndUserEmail(id, authentication.getName())
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        Category category = categoryRepository.findByIdAndUserEmail(request.categoryId(), authentication.getName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        budget.setLimitAmount(request.limitAmount());
        budget.setMonth(YearMonth.of(request.year(), request.month()));
        budget.setCategory(category);

        return map(budgetRepository.save(budget));
    }

    public void delete(Long id, Authentication authentication) {
        Budget budget = budgetRepository.findByIdAndUserEmail(id, authentication.getName())
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        budgetRepository.delete(budget);
    }

    private BudgetResponse map(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getLimitAmount(),
                budget.getMonth().getYear(),
                budget.getMonth().getMonthValue(),
                budget.getCategory().getId(),
                budget.getCategory().getName()
        );
    }
}