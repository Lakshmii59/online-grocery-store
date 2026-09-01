package com.grocery.productcatalogservice.mapper;

import com.grocery.productcatalogservice.dto.CategoryRequestDto;
import com.grocery.productcatalogservice.dto.CategoryResponseDto;
import com.grocery.productcatalogservice.entity.Category;

public class CategoryMapper {
    private CategoryMapper(){}
    public static Category toEntity(CategoryRequestDto dto){
        return Category.builder()
                .categoryName(dto.categoryName())
                .description(dto.description())
                .build();
    }

    public static CategoryResponseDto toResponseDto(Category entity){
        return new CategoryResponseDto(
                entity.getCategoryId(),
                entity.getCategoryName(),
                entity.getDescription()
                );

    }
}
