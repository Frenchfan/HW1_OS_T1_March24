package com.sumkin.hw1_openschool.domain.repository;

import com.sumkin.hw1_openschool.domain.entities.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaRepository<Product, Long> {
    @Query("SELECT p FROM products p WHERE p.price <= :maxPrice")
    List<Product> getProductsByMaxPrice(@Param("maxPrice") BigDecimal maxPrice, Pageable pageable);

    @Query("SELECT p FROM products p WHERE p.description LIKE %:description%")
    List<Product> findProductsByDescriptionContaining(@Param("description") String description);


}
