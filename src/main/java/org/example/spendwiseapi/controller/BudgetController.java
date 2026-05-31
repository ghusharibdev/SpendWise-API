package org.example.spendwiseapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.spendwiseapi.dto.BudgetRequest;
import org.example.spendwiseapi.dto.BudgetResponse;
import org.example.spendwiseapi.service.BudgetService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public BudgetResponse create(@Valid @RequestBody BudgetRequest request, Authentication authentication) {
        return budgetService.create(request, authentication);
    }

    @GetMapping
    public List<BudgetResponse> getAll(Authentication authentication) {
        return budgetService.getAll(authentication);
    }

    @PutMapping("/{id}")
    public BudgetResponse update(@PathVariable Long id, @Valid @RequestBody BudgetRequest request, Authentication authentication) {
        return budgetService.update(id, request, authentication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication authentication) {
        budgetService.delete(id, authentication);
    }
}