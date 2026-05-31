package org.example.spendwiseapi.service;


import lombok.RequiredArgsConstructor;
import org.example.spendwiseapi.dto.*;
import org.example.spendwiseapi.model.*;
import org.example.spendwiseapi.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final CurrentUserService currentUserService;

    public TransactionResponse create(TransactionRequest request, Authentication authentication) {
        AppUser user = currentUserService.getUser(authentication);

        Category category = categoryRepository.findByIdAndUserEmail(request.categoryId(), authentication.getName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = Transaction.builder()
                .title(request.title())
                .amount(request.amount())
                .type(request.type())
                .date(request.date())
                .note(request.note())
                .category(category)
                .user(user)
                .build();

        return map(transactionRepository.save(transaction));
    }

    public List<TransactionResponse> getAll(Authentication authentication) {
        return transactionRepository.findByUserEmailOrderByDateDescIdDesc(authentication.getName())
                .stream()
                .map(this::map)
                .toList();
    }

    public List<TransactionResponse> filter(LocalDate startDate, LocalDate endDate, Long categoryId, TransactionType type, Authentication authentication) {
        if (startDate != null && endDate != null) {
            return transactionRepository.findByUserEmailAndDateBetweenOrderByDateDescIdDesc(authentication.getName(), startDate, endDate)
                    .stream()
                    .map(this::map)
                    .toList();
        }

        if (categoryId != null) {
            return transactionRepository.findByUserEmailAndCategoryIdOrderByDateDescIdDesc(authentication.getName(), categoryId)
                    .stream()
                    .map(this::map)
                    .toList();
        }

        if (type != null) {
            return transactionRepository.findByUserEmailAndTypeOrderByDateDescIdDesc(authentication.getName(), type)
                    .stream()
                    .map(this::map)
                    .toList();
        }

        return getAll(authentication);
    }

    public TransactionResponse update(Long id, TransactionRequest request, Authentication authentication) {
        Transaction transaction = transactionRepository.findByIdAndUserEmail(id, authentication.getName())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Category category = categoryRepository.findByIdAndUserEmail(request.categoryId(), authentication.getName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        transaction.setTitle(request.title());
        transaction.setAmount(request.amount());
        transaction.setType(request.type());
        transaction.setDate(request.date());
        transaction.setNote(request.note());
        transaction.setCategory(category);

        return map(transactionRepository.save(transaction));
    }

    public void delete(Long id, Authentication authentication) {
        Transaction transaction = transactionRepository.findByIdAndUserEmail(id, authentication.getName())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transactionRepository.delete(transaction);
    }

    private TransactionResponse map(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getTitle(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDate(),
                transaction.getNote(),
                transaction.getCategory().getId(),
                transaction.getCategory().getName()
        );
    }
}