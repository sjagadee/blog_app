package com.springboot.blog.controller;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name = "CRUD REST APIs for Category Resource"
)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategoryById(@RequestBody CategoryDto categoryDto, @PathVariable(name = "id") Long categoryId) {
        return ResponseEntity.ok(categoryService.updateCategoryById(categoryDto, categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable(name = "id") Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(STR."Category with id: \{categoryId} deleted successfully", HttpStatus.OK);
    }

}
