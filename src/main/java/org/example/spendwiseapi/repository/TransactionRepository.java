package org.example.spendwiseapi.repository;


import org.example.spendwiseapi.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserEmailOrderByDateDescIdDesc(String email);
    Optional<Transaction> findByIdAndUserEmail(Long id, String email);
    List<Transaction> findByUserEmailAndDateBetweenOrderByDateDescIdDesc(String email, LocalDate start, LocalDate end);
    List<Transaction> findByUserEmailAndCategoryIdOrderByDateDescIdDesc(String email, Long categoryId);
    List<Transaction> findByUserEmailAndTypeOrderByDateDescIdDesc(String email, TransactionType type);
}