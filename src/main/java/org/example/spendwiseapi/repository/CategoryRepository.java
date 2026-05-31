package org.example.spendwiseapi.repository;

import org.example.spendwiseapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserEmailOrderByNameAsc(String email);
    Optional<Category> findByIdAndUserEmail(Long id, String email);
}