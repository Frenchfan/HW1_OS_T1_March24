package com.sumkin.hw1_openschool.domain.web.dto;

import com.sumkin.hw1_openschool.domain.entities.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "Category DTO to get categories without products")
@Builder
public class CategoryDto {

    @Schema(description = "Category id")
    private Long id;

    @Schema(description = "Category name")
    private String name;

    public static List<CategoryDto> entityToDto(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for (Category category : categories) {
            categoryDtos.add(CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build());
        }
        return categoryDtos;
    }

    public static CategoryDto entityToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}


