package org.example.spendwiseapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.spendwiseapi.dto.*;
import org.example.spendwiseapi.model.TransactionType;
import org.example.spendwiseapi.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse create(@Valid @RequestBody TransactionRequest request, Authentication authentication) {
        return transactionService.create(request, authentication);
    }

    @GetMapping
    public List<TransactionResponse> getAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) TransactionType type,
            Authentication authentication
    ) {
        return transactionService.filter(startDate, endDate, categoryId, type, authentication);
    }

    @PutMapping("/{id}")
    public TransactionResponse update(@PathVariable Long id, @Valid @RequestBody TransactionRequest request, Authentication authentication) {
        return transactionService.update(id, request, authentication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication authentication) {
        transactionService.delete(id, authentication);
    }
}