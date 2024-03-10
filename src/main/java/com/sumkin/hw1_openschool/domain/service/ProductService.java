package com.sumkin.hw1_openschool.domain.service;

import com.sumkin.hw1_openschool.domain.entities.product.Product;
import com.sumkin.hw1_openschool.domain.exception.ResourceNotFoundException;
import com.sumkin.hw1_openschool.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts(int page, int size, String sort) {

        String[] sortParams = sort.split(",");
        Sort sortObj = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        List<Product> products = productRepository.findAll(pageable).getContent();
        if (products.size() == 0) {
            throw new ResourceNotFoundException("No products found");
        }
        return products;
    }


    @Transactional(readOnly = true)
    public List<Product> getProductsByMaxPrice(int page, int size, String sort, BigDecimal maxPrice) {
        String[] sortParams = sort.split(",");
        Sort sortObj = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        List<Product> products = productRepository.getProductsByMaxPrice(maxPrice, pageable);
        if (products.size() == 0) {
            throw new ResourceNotFoundException("No products found");
        }
        return products;
    }


    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategoryId(product.getCategoryId());
        existingProduct.setRating(product.getRating());
        existingProduct.setReview(product.getReview());
        return productRepository.save(existingProduct);
    }

    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product updateRating(Long id, Short rating) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        existingProduct.setRating(rating);
        return productRepository.save(existingProduct);
    }

    @Transactional
    public Product updateReview(Long id, String review) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        existingProduct.setReview(review);
        return productRepository.save(existingProduct);
    }


    @Transactional(readOnly = true)
    public List<Product> getProductsByDescription(String name) {

        List<Product> products = productRepository.findProductsByDescriptionContaining(name);
        if (products.size() == 0) {
            throw new ResourceNotFoundException("No products found");
        }
        return products;
    }
}
