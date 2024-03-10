package com.example.testservice.model;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class Product {


    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Short rating;
    private String review;

}
