package org.example.spendwiseapi.service;


import lombok.RequiredArgsConstructor;
import org.example.spendwiseapi.dto.*;
import org.example.spendwiseapi.model.*;
import org.example.spendwiseapi.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CurrentUserService currentUserService;

    public CategoryResponse create(CategoryRequest request, Authentication authentication) {
        AppUser user = currentUserService.getUser(authentication);

        Category category = Category.builder()
                .name(request.name())
                .type(request.type())
                .user(user)
                .build();

        return map(categoryRepository.save(category));
    }

    public List<CategoryResponse> getAll(Authentication authentication) {
        return categoryRepository.findByUserEmailOrderByNameAsc(authentication.getName())
                .stream()
                .map(this::map)
                .toList();
    }

    public CategoryResponse update(Long id, CategoryRequest request, Authentication authentication) {
        Category category = categoryRepository.findByIdAndUserEmail(id, authentication.getName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.name());
        category.setType(request.type());

        return map(categoryRepository.save(category));
    }

    public void delete(Long id, Authentication authentication) {
        Category category = categoryRepository.findByIdAndUserEmail(id, authentication.getName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }

    private CategoryResponse map(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getType());
    }
}