package org.example.spendwiseapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.spendwiseapi.dto.CategoryRequest;
import org.example.spendwiseapi.dto.CategoryResponse;
import org.example.spendwiseapi.service.CategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request, Authentication authentication) {
        return categoryService.create(request, authentication);
    }

    @GetMapping
    public List<CategoryResponse> getAll(Authentication authentication) {
        return categoryService.getAll(authentication);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request, Authentication authentication) {
        return categoryService.update(id, request, authentication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication authentication) {
        categoryService.delete(id, authentication);
    }
}