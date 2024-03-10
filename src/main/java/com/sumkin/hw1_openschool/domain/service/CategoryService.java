package com.sumkin.hw1_openschool.domain.service;

import com.sumkin.hw1_openschool.domain.entities.category.Category;
import com.sumkin.hw1_openschool.domain.entities.product.Product;
import com.sumkin.hw1_openschool.domain.exception.ResourceNotFoundException;
import com.sumkin.hw1_openschool.domain.repository.CategoryRepository;
import com.sumkin.hw1_openschool.domain.web.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getAllCategories(int page, int size, String sort) {

        String[] sortParams = sort.split(",");
        Sort sortObj = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found");
        }
        return categories;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found");
        }
        return CategoryDto.entityToDto(categories);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsForCategory(Long categoryId, int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort sortObj = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        List<Product> products = categoryRepository.findProductsByCategoryId(categoryId, pageable);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found");
        }
        return products;
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).map(CategoryDto::entityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }


    @Transactional
    public CategoryDto updateCategory(Long categoryId, Category updatedCategory) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setName(updatedCategory.getName());
        return CategoryDto.entityToDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto createCategory(Category category) {
        return CategoryDto.entityToDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}
