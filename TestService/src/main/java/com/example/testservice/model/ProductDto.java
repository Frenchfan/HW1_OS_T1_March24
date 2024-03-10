package com.example.testservice.model;


import lombok.*;


import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class ProductDto {


    private String name;
    private String description;
    private BigDecimal price;
    private Short categoryId;
    private Short rating;
    private String review;

}
