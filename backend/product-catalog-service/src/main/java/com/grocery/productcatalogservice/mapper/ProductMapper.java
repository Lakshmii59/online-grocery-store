package com.grocery.productcatalogservice.mapper;

import com.grocery.productcatalogservice.dto.ProductRequestDto;
import com.grocery.productcatalogservice.dto.ProductResponseDto;
import com.grocery.productcatalogservice.entity.Category;
import com.grocery.productcatalogservice.entity.Product;

public class ProductMapper {
    private ProductMapper(){}
    public static Product toEntity(ProductRequestDto dto, Category category){
        return Product.builder()
                .productName(dto.productName())
                .sku(dto.sku())
                .price(dto.price())
                .availableQuantity(dto.availableQuantity())
                .active(dto.active())
                .imageUrl(dto.imageUrl())
                .category(category)
                .build();
    }

    public static ProductResponseDto toResponseDto(Product entity){
        return new ProductResponseDto(
                entity.getProductId(),
                entity.getProductName(),
                entity.getSku(),
                entity.getPrice(),
                entity.getAvailableQuantity(),
                entity.getActive(),
                entity.getImageUrl(),
                entity.getCategory().getCategoryId()
        );
    }


}
