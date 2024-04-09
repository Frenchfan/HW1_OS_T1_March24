package com.sumkin.hw1_openschool.domain.entities.product;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sumkin.hw1_openschool.domain.entities.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;


@Entity(name = "products")
@Schema(description = "Product entity")
@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Product id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(description = "Product name")
    @NotNull(message = "product name cannot be null")
    @Length(max = 255, message = "product name cannot be longer than 255 characters")
    private String name;

    @Schema(description = "Product description")
    @Length(max = 255, message = "product description cannot be longer than 255 characters")
    private String description;

    @Schema(description = "Product price")
    @NotNull(message = "product price cannot be null")
    private BigDecimal price;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY,description = "Product category")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonSerialize(using = CategorySerializer.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Category category;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, description = "Product category id")
    @Column(name = "category_id")
    private Short categoryId;

    @Schema(description = "Product rating")
    private Short rating;
    
    @Schema(description = "Product review")
    private String review;
}
