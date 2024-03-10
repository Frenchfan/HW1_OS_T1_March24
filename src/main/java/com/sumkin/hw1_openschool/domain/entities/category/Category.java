package com.sumkin.hw1_openschool.domain.entities.category;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sumkin.hw1_openschool.domain.entities.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import java.util.List;

@Entity(name = "categories")
@Schema(description = "Category entity")
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Category id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Schema(description = "Category name")
    @NotNull(message = "category name cannot be null")
    @Length(max = 255, message = "category name cannot be longer than 255 characters")
    private String name;

    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH
    )
    @Schema(accessMode = Schema.AccessMode.READ_ONLY,
            description = "List of products grouped by category")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Product> products;

    @PreRemove
    public void preRemove() {
        for (Product product : products) {
            product.setCategoryId(null);
        }
    }


}
