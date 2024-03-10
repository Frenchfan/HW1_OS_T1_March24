package com.sumkin.hw1_openschool.domain.web.controller;

import com.sumkin.hw1_openschool.domain.entities.category.Category;
import com.sumkin.hw1_openschool.domain.entities.product.Product;
import com.sumkin.hw1_openschool.domain.service.CategoryService;
import com.sumkin.hw1_openschool.domain.web.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category Controller", description = "Category API")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://*:8083")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    @Operation(summary = "Find all categories with pagination and sorting and all products by categories " +
            "- this endpoint is just to show the OneToMany relationship")
    @GetMapping("/with-products")
    public List<Category> findAllCategories(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size,
                                          @RequestParam(defaultValue = "id,asc") String sort) {
        log.info("Finding all categories with pagination and sorting, page = " + page + ", " +
                "size = " + size + ", sort = " + sort);
        return categoryService.getAllCategories(page, size, sort);
    }

    @Operation(summary = "Find all categories ")
    @GetMapping
    public List<CategoryDto> findAllCategories() {
        log.info("Finding all categories");
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Find products for category with pagination and sorting")
    @GetMapping("/{categoryId}/products")
    public List<Product> findProductsForCategory(@PathVariable Long categoryId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size,
                                                 @RequestParam(defaultValue = "id,asc") String sort) {
        log.info("Finding products for category with pagination and sorting, page = " + page + ", " +
                "size = " + size + ", sort = " + sort);
        return categoryService.getProductsForCategory(categoryId, page, size, sort);
    }

    @Operation(summary = "Find category by id")
    @GetMapping("/{id}")
    public CategoryDto findCategoryById(@PathVariable Long id) {
        log.info("Finding category by id: " + id);
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category")
    public CategoryDto updateCategory(@PathVariable Long id, @Validated
                                   @RequestBody Category category) {
        log.info("Updating category: " + id);
        return categoryService.updateCategory(id, category);
    }

    @PostMapping
    @Operation(summary = "Create category")
    public CategoryDto createCategory(@Validated
                                   @RequestBody Category category) {
        log.info("Creating category: " + category);
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category")
    public void deleteCategory(@PathVariable Long id) {
        log.info("Deleting category: " + id);
        categoryService.deleteCategory(id);
    }


}
