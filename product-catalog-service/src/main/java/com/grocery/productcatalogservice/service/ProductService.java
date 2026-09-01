package com.grocery.productcatalogservice.service;

import com.grocery.productcatalogservice.dto.*;

import java.util.List;

public interface ProductService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto getCategoryById(Long categoryId);
    CategoryResponseDto updateCategory(Long categoryId,CategoryRequestDto categoryRequestDto);
    void deleteCategory(Long categoryId);

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto getProductById(Long productID);
    ProductResponseDto updateProduct(Long productID, ProductRequestDto productRequestDto);
    void deleteProduct(Long productID);

}
