package com.example.testservice.service;

import com.example.testservice.model.Category;
import com.example.testservice.model.Product;
import com.example.testservice.model.ProductDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Service
public class TestService {


    @Value("${myUrl}")
    private String url;
    private final RestTemplate restTemplate;

    @PostConstruct
    public void init() {

        ProductDto newProduct = ProductDto.builder()
                .name("orange")
                .description("fresh orange")
                .price(new BigDecimal("18.2"))
                .categoryId((short) 1)
                .rating((short) 5)
                .review("test for reviewing an orange")
                .build();

        ResponseEntity<Product[]> responseProduct = restTemplate.getForEntity(url + "/products?size=50",
                Product[].class);
        Product[] products = responseProduct.getBody();
        log.info("List of all products: {}", (Object) products);
        ResponseEntity<Category[]> responseCategory = restTemplate.getForEntity(url + "/categories",
                Category[].class);
        Category[] categories = responseCategory.getBody();
        log.info("List of all categories: {}", (Object) categories);

        Product product = restTemplate.postForObject(url + "/products", newProduct, Product.class);
        log.info("newProduct created: {}", newProduct);

        ProductDto updatedProduct = ProductDto.builder()
                .name("Big sweater")
                .description("A very warm one")
                .price(new BigDecimal("45.15"))
                .categoryId((short) 2)
                .rating((short) 5)
                .review("test for reviewing an orange")
                .build();

        ProductDto updatedProductResponse = restTemplate.exchange(url + "/products/{id}", HttpMethod.PUT,
                new HttpEntity<>(updatedProduct), ProductDto.class, 5).getBody();
        log.info("Product updated: {}", updatedProduct);

        Product product5 = restTemplate.getForObject(url + "/products/5", Product.class);
        log.info("Test of getting a product: {}", product5);

        if (product != null) {
            log.info("Сейчас удалим продукт с id = " + product.getId());
            restTemplate.delete(url + "/products/{id}", product.getId());
        }

        ResponseEntity<Product[]> responseProduct2 = restTemplate.getForEntity(url + "/products?size=50",
                Product[].class);
        Product[] updatedProducts = responseProduct2.getBody();
        log.info("List of all products - the new product has been deleted: {}", (Object) updatedProducts);

        ResponseEntity<Product[]> responseProduct3 = restTemplate.getForEntity(url + "/products?size=50&maxPrice=11",
                Product[].class);
        Product[] filteredByPriceProducts = responseProduct3.getBody();
        log.info("List of products filtered by price - max 11: {}", (Object) filteredByPriceProducts);

        ResponseEntity<Product[]> responseProduct4 = restTemplate.getForEntity(url + "/categories/1/products",
                Product[].class);
        Product[] filteredByCategoryProducts = responseProduct4.getBody();
        log.info("List of products filtered by category - Food: {}", (Object) filteredByCategoryProducts);


    }
}
