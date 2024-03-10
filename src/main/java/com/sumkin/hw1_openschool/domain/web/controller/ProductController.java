package com.sumkin.hw1_openschool.domain.web.controller;

import com.sumkin.hw1_openschool.domain.entities.product.Product;
import com.sumkin.hw1_openschool.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Product API")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://*:8083")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Find all Products with pagination and sorting")
    @GetMapping
    public List<Product> findAllProducts(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size,
                                         @RequestParam(defaultValue = "id,asc") String sort,
                                         @RequestParam(required = false) BigDecimal maxPrice) {
        log.info("Finding all products with pagination and sorting, page = " + page + ", " +
                "size = " + size + ", sort = " + sort);
        if (maxPrice != null) {
            return productService.getProductsByMaxPrice(page, size, sort, maxPrice);
        } else {
            return productService.getAllProducts(page, size, sort);
        }
    }

    @GetMapping("contains")
    @Operation(summary = "Find products by description containing words")
    public List<Product> findProductsByDescription(@RequestParam String name) {
        log.info("Finding products by description containing words: " + name);
        return productService.getProductsByDescription(name);
    }

    @Operation(summary = "Find product by id")
    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id) {
        log.info("Finding product by id: " + id);
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public Product updateProduct(@PathVariable Long id, @Validated @RequestBody Product product) {
        log.info("Updating product: " + id);
        return productService.updateProduct(id, product);
    }

    @PostMapping
    @Operation(summary = "Create product")
    public Product createProduct(@Validated @RequestBody Product product) {
        log.info("Creating product: " + product);
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    public void deleteProduct(@PathVariable Long id) {
        log.info("Deleting product: " + id);
        productService.deleteProduct(id);
    }

    @PutMapping("{id}/rating")
    @Operation(summary = "Update rating")
    public Product updateRating(@PathVariable Long id, @Validated @RequestBody Short rating) {
        log.info("Updating rating: " + rating);
        return productService.updateRating(id, rating);
    }

    @PutMapping("{id}/review")
    @Operation(summary = "Update review")
    public Product updateReview(@PathVariable Long id, @Validated @RequestBody String review) {
        log.info("Updating review: " + review);
        return productService.updateReview(id, review);
    }
}
